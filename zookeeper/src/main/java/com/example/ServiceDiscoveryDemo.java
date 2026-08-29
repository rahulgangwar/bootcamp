package com.example;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class ServiceDiscoveryDemo implements Watcher {
    private static final String ZOOKEEPER_SERVER = "localhost:2181";
    private static final int SESSION_TIMEOUT = 10_000;
    private static final String SERVICE_PATH = "/services/order";

    private final CountDownLatch connected = new CountDownLatch(1);
    private ZooKeeper zooKeeper;
    private String instanceId;

    public void start() throws Exception {
        instanceId = UUID.randomUUID().toString();
        System.out.println("Starting OrderService");
        System.out.println("Instance ID: " + instanceId);

        zooKeeper = new ZooKeeper(ZOOKEEPER_SERVER, SESSION_TIMEOUT, this);
        connected.await();
        System.out.println("Connected to ZooKeeper");

        createParentIfNeeded();
        registerService();
        discoverServices();

        System.out.println("Service is running...");
        System.out.println("Press Ctrl+C to stop.");
        Thread.currentThread().join();
    }

    private void createParentIfNeeded() throws Exception {
        if (zooKeeper.exists(SERVICE_PATH, false) == null) {
            try {
                zooKeeper.create(
                        SERVICE_PATH,
                        new byte[0],
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
                System.out.println("Created parent path: " + SERVICE_PATH);
            } catch (KeeperException.NodeExistsException ignored) {
                // Another service created it concurrently
            }
        }
    }

    private void registerService() throws Exception {
        String path = SERVICE_PATH + "/" + instanceId;
        String address = "localhost:8080";

        zooKeeper.create(
                path,
                address.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);

        System.out.println("Registered service:");
        System.out.println("Path: " + path);
        System.out.println("Address: " + address);
    }

    private void discoverServices() throws Exception {
        List<String> instances = zooKeeper.getChildren(SERVICE_PATH, this);

        System.out.println("\nAvailable OrderService instances:");

        for (String instance : instances) {
            String path = SERVICE_PATH + "/" + instance;
            byte[] data = zooKeeper.getData(path, false, null);
            String address = new String(data, StandardCharsets.UTF_8);
            System.out.println(instance + " -> " + address);
        }

        System.out.println();
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("ZooKeeper event: " + event);

        if (event.getState() == Event.KeeperState.SyncConnected) {
            connected.countDown();
        }

        if (event.getType() == Event.EventType.NodeChildrenChanged) {
            try {
                System.out.println("\nService registry changed!");
                discoverServices();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ServiceDiscoveryDemo().start();
    }
}
