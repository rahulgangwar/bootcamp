package com.example;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LeaderElectionDemo {
    private static final String ZOOKEEPER_SERVER = "localhost:2181";
    private static final int SESSION_TIMEOUT = 10_000;
    private static final String ELECTION_PATH = "/election";

    public static void main(String[] args) throws Exception {
        createElectionPath();

        Thread node1 = new Thread(new ElectionNode("Node-1"), "Node-1");
        Thread node2 = new Thread(new ElectionNode("Node-2"), "Node-2");
        Thread node3 = new Thread(new ElectionNode("Node-3"), "Node-3");

        node1.start();
        Thread.sleep(5000);

        node2.start();
        Thread.sleep(5000);

        node3.start();

        node1.join();
        node2.join();
        node3.join();
    }

    private static void createElectionPath() throws Exception {
        try (ZooKeeper zooKeeper = new ZooKeeper(ZOOKEEPER_SERVER, SESSION_TIMEOUT, event -> {})) {
            Thread.sleep(1000);

            if (zooKeeper.exists(ELECTION_PATH, false) == null) {
                zooKeeper.create(
                        ELECTION_PATH,
                        new byte[0],
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            }
        }
    }

    static class ElectionNode implements Runnable, Watcher {
        private final String nodeName;
        private final CountDownLatch connected = new CountDownLatch(1);
        private ZooKeeper zooKeeper;
        private String myNodePath;

        ElectionNode(String nodeName) {
            this.nodeName = nodeName;
        }

        @Override
        public void run() {
            try {
                start();
            } catch (Exception e) {
                System.out.println(nodeName + " failed: " + e.getMessage());
            }
        }

        private void start() throws Exception {
            zooKeeper = new ZooKeeper(ZOOKEEPER_SERVER, SESSION_TIMEOUT, this);
            connected.await();

            System.out.println(nodeName + " connected to ZooKeeper");

            myNodePath =
                    zooKeeper.create(
                            ELECTION_PATH + "/candidate-",
                            new byte[0],
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.EPHEMERAL_SEQUENTIAL);

            System.out.println(nodeName + " created " + myNodePath);
            checkLeadership();

            Thread.sleep(15_000);
            System.out.println("\n" + nodeName + " simulating failure...\n");
            zooKeeper.close();
        }

        private void checkLeadership() throws Exception {
            List<String> children = zooKeeper.getChildren(ELECTION_PATH, false);
            children.sort(String::compareTo);

            String myNodeName = myNodePath.substring(myNodePath.lastIndexOf('/') + 1);

            if (myNodeName.equals(children.get(0))) {
                System.out.println(nodeName + " >>> I AM THE LEADER <<<");
                return;
            }

            String predecessor = findPredecessor(children, myNodeName);

            System.out.println(nodeName + " is follower");
            System.out.println(nodeName + " watching " + predecessor);

            zooKeeper.exists(ELECTION_PATH + "/" + predecessor, this);
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

        @Override
        public void process(WatchedEvent event) {
            System.out.println(nodeName + " received event: " + event);

            if (event.getState() == Event.KeeperState.SyncConnected) {
                connected.countDown();
            }

            if (event.getType() == Event.EventType.NodeDeleted) {
                try {
                    System.out.println(nodeName + " detected predecessor failure");
                    checkLeadership();
                } catch (Exception e) {
                    System.out.println(nodeName + " election check failed: " + e.getMessage());
                }
            }
        }
    }
}
