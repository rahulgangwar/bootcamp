package com.example;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class UserSession {
    public static void main(String[] args) {

        try (Jedis jedis = new Jedis("localhost", 6379)) {
            String sessionKey = "session:user:101";
            // Create session
            // HSET session:user:101 userId 101 username rahul role USER
            jedis.hset(
                    sessionKey,
                    Map.of(
                            "userId", "101",
                            "username", "rahul",
                            "role", "USER"));

            // Session expires after 30 minutes
            // EXPIRE session:user:101 1800
            jedis.expire(sessionKey, 10);

            // Read session
            // HGETALL session:user:101
            Map<String, String> session = jedis.hgetAll(sessionKey);
            System.out.println(session);

            // Check remaining TTL
            // TTL session:user:101
            long ttl = jedis.ttl(sessionKey);
            while (ttl > 0) {
                ttl = jedis.ttl(sessionKey);
                System.out.println("TTL = " + ttl);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
