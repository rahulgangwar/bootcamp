package com.example;

import lombok.SneakyThrows;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DistributedLock {

    private static final String LOCK_KEY = "lock:order:101";
    private static final int LOCK_EXPIRY_SECONDS = 10;

    public static void main(String[] args) {

        int numberOfThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 1; i <= numberOfThreads; i++) {
            executor.submit(new OrderProcessor(i));
        }
        executor.shutdown();
    }

    private static class OrderProcessor implements Runnable {
        Jedis jedis;
        int threadId;

        public OrderProcessor(int threadId) {
            this.jedis = new Jedis("localhost", 6379);
            this.threadId = threadId;
        }

        @SneakyThrows
        @Override
        public void run() {
            String lockValue = UUID.randomUUID().toString();
            // Try to acquire lock
            // SET lock:order:101 <unique-value> NX EX 10
            String result =
                    jedis.set(LOCK_KEY, lockValue, new SetParams().nx().ex(LOCK_EXPIRY_SECONDS));
            while (result == null) {
                result =
                        jedis.set(
                                LOCK_KEY, lockValue, new SetParams().nx().ex(LOCK_EXPIRY_SECONDS));
                System.out.println("Thread " + threadId + " → waiting..");
                Thread.sleep(1000);
            }

            System.out.println("Thread " + threadId + " → LOCK ACQUIRED");
            Thread.sleep(1000);
            System.out.println("Thread " + threadId + " → Processed Order");

            // Release lock
            // DEL lock:order:101
            jedis.del(LOCK_KEY);
            System.out.println("Thread " + threadId + " → LOCK RELEASED");
        }
    }
}
