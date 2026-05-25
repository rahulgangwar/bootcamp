package com.example.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ZookeeperConfigWatcher {

    private static final String ZK_ADDRESS = "localhost:2181";
    private static final String CONFIG_NODE_PATH = "/config/db_url";
    private ZooKeeper zooKeeper;
    private final Watcher watcher =
            new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeDataChanged
                            && event.getPath().equals(CONFIG_NODE_PATH)) {
                        try {
                            System.out.println(
                                    "Data at node : "
                                            + CONFIG_NODE_PATH
                                            + "  >>> "
                                            + getDataForNode(CONFIG_NODE_PATH));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

    public static void main(String[] args) throws Exception {
        ZookeeperConfigWatcher watcher = new ZookeeperConfigWatcher();
        watcher.connect();
        watcher.createNode(
                CONFIG_NODE_PATH,
                "Initial value",
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(
                "Data at node : "
                        + CONFIG_NODE_PATH
                        + "  >>> "
                        + watcher.getDataForNode(CONFIG_NODE_PATH));

        // Keep the app running
        Thread.sleep(Long.MAX_VALUE);
    }

    public void connect() throws IOException {
        zooKeeper = new ZooKeeper(ZK_ADDRESS, 3000, null);
    }

    public void createNode(
            String nodePath, String initialValue, ArrayList<ACL> acls, CreateMode createMode)
            throws InterruptedException, KeeperException {
        Stat stat = zooKeeper.exists(nodePath, watcher);
        if (Objects.isNull(stat)) {
            String path =
                    zooKeeper.create(
                            nodePath,
                            initialValue.getBytes(),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
            System.out.println("Node created at path : " + path);
        } else {
            System.out.println("Node already exists : " + stat);
        }
    }

    public String getDataForNode(String nodePath) throws InterruptedException, KeeperException {
        byte[] data = zooKeeper.getData(nodePath, watcher, null);
        return new String(data);
    }
}
