package com.example.multithreading;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3); // 3 tasks to complete

        Thread configLoader =
                new Thread(
                        () -> {
                            System.out.println("Loading configuration...");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {
                            }
                            System.out.println("Configuration loaded.");
                            latch.countDown();
                        });

        Thread dbConnector =
                new Thread(
                        () -> {
                            System.out.println("Connecting to database...");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ignored) {
                            }
                            System.out.println("Database connected.");
                            latch.countDown();
                        });

        Thread cacheInitializer =
                new Thread(
                        () -> {
                            System.out.println("Initializing cache...");
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException ignored) {
                            }
                            System.out.println("Cache initialized.");
                            latch.countDown();
                        });

        // Start all tasks
        configLoader.start();
        dbConnector.start();
        cacheInitializer.start();

        // Wait for all tasks to complete
        System.out.println("Main thread waiting for tasks to complete...");
        latch.await();

        System.out.println("All services are up. Server is starting...");
    }
}
