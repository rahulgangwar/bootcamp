package com.example.multithreading;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/*
   Virtual threads are lightweight JVM-managed threads introduced in Java 21
   that allow applications to run very large numbers of concurrent tasks efficiently,
   particularly for I/O-bound workloads, by multiplexing virtual threads
   over a smaller number of platform/carrier threads.
*/
public class VirtualThreadsDemo {
    static final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        virtualThreads();
        virtualThreadsUsingExecutorService();
    }

    private static void virtualThreads() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            Thread thread = Thread.ofVirtual().start(VirtualThreadsDemo::process);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static void virtualThreadsUsingExecutorService() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10_000; i++) {
                executor.submit(VirtualThreadsDemo::process);
            }
        }
    }

    private static void process() {
        try {
            Thread.sleep(Duration.ofSeconds(1));
            System.out.println("Done: " + count.incrementAndGet());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
