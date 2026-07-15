package com.example.multithreading.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorMonitor implements Runnable {

    private final ThreadPoolExecutor executor;

    public ExecutorMonitor(ExecutorService executor) {
        this.executor = (ThreadPoolExecutor) executor;
    }

    @Override
    public void run() {
        while (!executor.isShutdown()) {
            System.out.println("\n========== Thread Pool ==========");
            System.out.println("Pool Size        : " + executor.getPoolSize());
            System.out.println("Active Threads   : " + executor.getActiveCount());
            System.out.println("Completed Tasks  : " + executor.getCompletedTaskCount());
            System.out.println("Queue Size       : " + executor.getQueue().size());
            System.out.println("===============================\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
