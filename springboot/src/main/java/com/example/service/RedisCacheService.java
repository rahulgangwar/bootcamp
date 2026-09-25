package com.example.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheService(
            RedisTemplate<String, Object> redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    public <T> void set(
            String key,
            T value,
            long timeout,
            TimeUnit unit) {

        redisTemplate.opsForValue()
                .set(key, value, timeout, unit);
    }

    public <T> T get(
            String key,
            Class<T> type) {

        Object value = redisTemplate
                .opsForValue()
                .get(key);

        if (value == null) {
            return null;
        }

        log.info("Redis cache hit for key: {}", key);
        return type.cast(value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}