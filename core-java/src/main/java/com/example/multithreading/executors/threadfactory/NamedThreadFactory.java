package com.example.multithreading.executors.threadfactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("report-worker-" + counter.getAndIncrement());
        return thread;
    }
}
