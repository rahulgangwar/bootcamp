package com.example;

import redis.clients.jedis.Jedis;

public class RateLimiter {

    private static final int MAX_REQUESTS = 5;
    private static final int WINDOW_SECONDS = 60;

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            String userId = "101";
            for (int i = 1; i <= 7; i++) {
                boolean allowed = isAllowed(jedis, userId);
                System.out.println("Request " + i + " -> " + (allowed ? "ALLOWED" : "REJECTED"));
            }
        }
    }

    private static boolean isAllowed(Jedis jedis, String userId) {
        String key = "rate-limit:user:" + userId;
        // ============================================================
        // INCREMENT REQUEST COUNT
        // ============================================================

        // Redis:
        // INCR rate-limit:user:101
        long count = jedis.incr(key);

        // ============================================================
        // SET EXPIRY FOR FIRST REQUEST
        // ============================================================

        if (count == 1) {
            // Redis:
            // EXPIRE rate-limit:user:101 60
            jedis.expire(key, WINDOW_SECONDS);
        }

        // ============================================================
        // CHECK LIMIT
        // ============================================================
        return count <= MAX_REQUESTS;
    }
}
