package com.example;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperDemo implements Watcher {

    private static final String ZOOKEEPER_SERVER = "localhost:2181";
    private static final int SESSION_TIMEOUT = 10_000;
    private final CountDownLatch connected = new CountDownLatch(1);
    private ZooKeeper zooKeeper;

    public void start() throws Exception {
        System.out.println("Connecting to ZooKeeper...");

        zooKeeper = new ZooKeeper(ZOOKEEPER_SERVER, SESSION_TIMEOUT, this);
        connected.await();
        System.out.println("Connected!");

        createServiceNode();
        readServiceNode();
        listServices();

        System.out.println();
        System.out.println("Application is running...");
        System.out.println("Press Ctrl+C to stop.");

        // Keep the JVM alive
        Thread.currentThread().join();
    }

    private void createServiceNode() throws Exception {
        String path = "/services/order/node-1";
        String data = "10.0.0.1:8080";
        String createdPath =
                zooKeeper.create(
                        path,
                        data.getBytes(StandardCharsets.UTF_8),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
        System.out.println("Created: " + createdPath);
    }

    private void readServiceNode() throws Exception {
        String path = "/services/order/node-1";
        byte[] data = zooKeeper.getData(path, false, null);
        System.out.println("Data: " + new String(data, StandardCharsets.UTF_8));
    }

    private void listServices() throws Exception {
        List<String> children = zooKeeper.getChildren("/services/order", false);
        System.out.println("Order service instances: " + children);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("ZooKeeper event: " + event);
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connected.countDown();
        }
    }

    public static void main(String[] args) throws Exception {
        new ZooKeeperDemo().start();
    }
}
