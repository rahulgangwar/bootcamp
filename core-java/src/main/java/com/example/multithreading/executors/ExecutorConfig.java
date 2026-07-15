package com.example.multithreading.executors;

import com.example.multithreading.executors.threadfactory.NamedThreadFactory;

import java.util.concurrent.*;

public class ExecutorConfig {

    private ExecutorConfig() {
    }

    public static ExecutorService createExecutor() {

        return new ThreadPoolExecutor(
                3,                          // Core threads
                5,                          // Max threads
                60,                         // Keep alive
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new NamedThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}