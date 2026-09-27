package com.example.service;

import com.example.dto.RateLimitConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.KeeperException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class RateLimiterService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final DefaultRedisScript<Long> rateLimiterScript;
    private final ZooKeeperConfigService zooKeeperConfigService;

    public RateLimiterService(
            RedisTemplate<String, Object> redisTemplate,
            ZooKeeperConfigService zooKeeperConfigService) {
        this.redisTemplate = redisTemplate;
        this.zooKeeperConfigService = zooKeeperConfigService;
        this.rateLimiterScript = new DefaultRedisScript<>();
        this.rateLimiterScript.setLocation(new ClassPathResource("scripts/rate-limiter.lua"));
        this.rateLimiterScript.setResultType(Long.class);
    }

    public boolean allowRequest(String key) throws InterruptedException, KeeperException {
        String redisKey = "rate_limit:ip:" + key;
        RateLimitConfig config = zooKeeperConfigService.getGlobalConfig();

        Long result =
                redisTemplate.execute(
                        rateLimiterScript,
                        List.of(redisKey),
                        config.windowSeconds(), // expiry: 60 seconds
                        config.limit() // max requests
                        );
        if (result == null || result == -1) {
            log.info("Rate limit exceeded for key: {}", key);
            return false;
        }
        return true;
    }
}
