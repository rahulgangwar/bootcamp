package com.example.multithreading.executors;

import com.example.multithreading.executors.threadfactory.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(
                3,              // Core threads
                5,                          // Max threads
                60,                         // Keep alive
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new NamedThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        ExecutorMonitor monitor = new ExecutorMonitor(executor);
        Thread monitorThread = new Thread(monitor);
        monitorThread.setDaemon(true);
        monitorThread.start();

        ReportGenerationService service = new ReportGenerationService(executor);
        service.generateMonthlyReport();
        executor.shutdown();
    }
}