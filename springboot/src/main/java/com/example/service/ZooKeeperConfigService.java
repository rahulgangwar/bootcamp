package com.example.service;

import com.example.dto.RateLimitConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component
public class ZooKeeperConfigService {

    private static final String ZK_SERVER = "localhost:2181";
    private static final String CONFIG_PATH = "/rate-limiter/global";

    private final ZooKeeper zooKeeper;

    public ZooKeeperConfigService() throws IOException {
        this.zooKeeper =
                new ZooKeeper(
                        ZK_SERVER,
                        3000,
                        event -> {
                            log.info("ZooKeeper event: {}", event);
                        });
    }

    public RateLimitConfig getGlobalConfig() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(CONFIG_PATH, false, null);
        String config = new String(data, StandardCharsets.UTF_8);
        return parse(config);
    }

    private RateLimitConfig parse(String config) {
        String[] parts = config.split(",");
        int limit = Integer.parseInt(parts[0].split("=")[1]);
        int window = Integer.parseInt(parts[1].split("=")[1]);
        return new RateLimitConfig(limit, window);
    }
}
