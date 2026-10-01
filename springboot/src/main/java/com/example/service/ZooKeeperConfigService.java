package com.example.service;

import com.example.dto.RateLimitConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component
public class ZooKeeperConfigService {
    private static final String ZK_SERVER = "localhost:2181";
    private static final String RATE_LIMITER_GLOBAL = "/rate-limiter/global";
    private static final int SESSION_TIMEOUT = 10_000;
    private volatile ZooKeeper zooKeeper;
    private volatile RateLimitConfig config;

    @PostConstruct
    public void init() throws Exception {
        connect();
    }

    private synchronized void connect() throws IOException {
        log.info("Connecting to ZooKeeper...");
        zooKeeper = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, this::handleEvent);
    }

    private void handleEvent(WatchedEvent event) {
        log.info("ZooKeeper event: type={}, state={}", event.getType(), event.getState());
        if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
            try {
                loadRateLimiterConfig();
            } catch (Exception e) {
                log.error("Failed to load rate limiter config", e);
            }
        }
        if (event.getState() == Watcher.Event.KeeperState.Expired) {
            log.error("ZooKeeper session expired. Reconnecting...");
            try {
                closeZooKeeper();
                connect();
            } catch (Exception e) {
                log.error("Failed to reconnect to ZooKeeper", e);
            }
        }
        if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
            try {
                loadRateLimiterConfig();
            } catch (Exception e) {
                log.error("Failed to reload rate limiter config", e);
            }
        }
    }

    private void loadRateLimiterConfig() throws KeeperException, InterruptedException {
        ZooKeeper zk = this.zooKeeper;
        byte[] data =
                zk.getData(
                        RATE_LIMITER_GLOBAL,
                        event -> {
                            log.info("Rate limiter config changed: {}", event);
                            if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
                                try {
                                    loadRateLimiterConfig();
                                } catch (Exception e) {
                                    log.error("Failed to reload config", e);
                                }
                            }
                        },
                        null);
        String configString = new String(data, StandardCharsets.UTF_8);
        RateLimitConfig newConfig = parse(configString);
        this.config = newConfig;
        log.info("Loaded rate limiter config: {}", newConfig);
    }

    public RateLimitConfig getGlobalConfig() {
        return config;
    }

    private RateLimitConfig parse(String config) {
        String[] parts = config.split(",");
        int limit = Integer.parseInt(parts[0].split("=")[1]);
        int window = Integer.parseInt(parts[1].split("=")[1]);
        return new RateLimitConfig(limit, window);
    }

    private synchronized void closeZooKeeper() {
        if (zooKeeper != null) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Interrupted while closing ZooKeeper", e);
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Closing ZooKeeper...");
        closeZooKeeper();
    }
}
