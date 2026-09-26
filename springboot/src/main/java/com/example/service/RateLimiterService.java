package com.example.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateLimiterService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final DefaultRedisScript<Long> rateLimiterScript;

    public RateLimiterService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.rateLimiterScript = new DefaultRedisScript<>();
        this.rateLimiterScript.setLocation(new ClassPathResource("scripts/rate-limiter.lua"));
        this.rateLimiterScript.setResultType(Long.class);
    }

    public boolean allowRequest(String key) {
        String redisKey = "rate_limit:ip:" + key;

        //Allow 10 requests per minute
        Long result =
                redisTemplate.execute(
                        rateLimiterScript,
                        List.of(redisKey),
                        60, // expiry: 60 seconds
                        10 // max 10 requests
                        );
        return result != null && result != -1;
    }
}
