package com.example;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DistributedLockDemo {
    private static final String ZOOKEEPER_SERVER = "localhost:2181";
    private static final int SESSION_TIMEOUT = 10_000;
    private static final String LOCK_PATH = "/locks/payment";

    public static void main(String[] args) throws Exception {
        createLockPath();

        Thread worker1 = new Thread(new LockWorker("Worker-1"), "Worker-1");
        Thread worker2 = new Thread(new LockWorker("Worker-2"), "Worker-2");
        Thread worker3 = new Thread(new LockWorker("Worker-3"), "Worker-3");

        worker1.start();
        Thread.sleep(1000);
        worker2.start();
        Thread.sleep(1000);
        worker3.start();

        worker1.join();
        worker2.join();
        worker3.join();
    }

    private static void createLockPath() throws Exception {
        try (ZooKeeper zooKeeper = new ZooKeeper(ZOOKEEPER_SERVER, SESSION_TIMEOUT, event -> {})) {
            Thread.sleep(1000);

            if (zooKeeper.exists("/locks", false) == null) {
                try {
                    zooKeeper.create(
                            "/locks",
                            new byte[0],
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                } catch (KeeperException.NodeExistsException ignored) {
                }
            }

            if (zooKeeper.exists(LOCK_PATH, false) == null) {
                try {
                    zooKeeper.create(
                            LOCK_PATH,
                            new byte[0],
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                } catch (KeeperException.NodeExistsException ignored) {
                }
            }
        }
    }

    static class LockWorker implements Runnable, Watcher {
        private final String workerName;
        private final CountDownLatch connected = new CountDownLatch(1);
        private ZooKeeper zooKeeper;
        private String myLockNode;

        LockWorker(String workerName) {
            this.workerName = workerName;
        }

        @Override
        public void run() {
            try {
                start();
            } catch (Exception e) {
                System.out.println(workerName + " failed: " + e.getMessage());
            }
        }

        private void start() throws Exception {
            zooKeeper = new ZooKeeper(ZOOKEEPER_SERVER, SESSION_TIMEOUT, this);
            connected.await();

            System.out.println(workerName + " connected");

            acquireLock();
            Thread.currentThread().join();
        }

        private void acquireLock() throws Exception {
            myLockNode =
                    zooKeeper.create(
                            LOCK_PATH + "/lock-",
                            new byte[0],
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.EPHEMERAL_SEQUENTIAL);

            System.out.println(workerName + " created " + myLockNode);
            checkLock();
        }

        private void checkLock() throws Exception {
            List<String> children = zooKeeper.getChildren(LOCK_PATH, false);
            children.sort(String::compareTo);

            String myNodeName = myLockNode.substring(myLockNode.lastIndexOf('/') + 1);

            if (myNodeName.equals(children.getFirst())) {
                acquireLockOwnership();
                return;
            }

            String predecessor = findPredecessor(children, myNodeName);
            System.out.println(workerName + " waiting for " + predecessor);
            zooKeeper.exists(LOCK_PATH + "/" + predecessor, this);
        }

        private String findPredecessor(List<String> children, String myNodeName) {
            String predecessor = null;

            for (String child : children) {
                if (child.compareTo(myNodeName) < 0) {
                    predecessor = child;
                } else {
                    break;
                }
            }

            return predecessor;
        }

        private void acquireLockOwnership() throws Exception {
            System.out.println();
            System.out.println(workerName + " >>> LOCK ACQUIRED <<<");

            performCriticalWork();
            releaseLock();
        }

        private void performCriticalWork() throws InterruptedException {
            System.out.println(workerName + " processing payment...");
            Thread.sleep(5000);
            System.out.println(workerName + " finished payment");
        }

        private void releaseLock() throws Exception {
            System.out.println(workerName + " releasing lock");

            zooKeeper.delete(myLockNode, -1);
            zooKeeper.close();
        }

        @Override
        public void process(WatchedEvent event) {
            System.out.println(workerName + " received event: " + event);

            if (event.getState() == Event.KeeperState.SyncConnected) {
                connected.countDown();
            }

            if (event.getType() == Event.EventType.NodeDeleted) {
                try {
                    System.out.println(workerName + " predecessor released lock");
                    checkLock();
                } catch (Exception e) {
                    System.out.println(workerName + " lock check failed: " + e.getMessage());
                }
            }
        }
    }
}
