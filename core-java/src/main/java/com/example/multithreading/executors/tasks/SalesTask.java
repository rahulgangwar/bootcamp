package com.example.multithreading.executors.tasks;

import java.util.concurrent.Callable;

public class SalesTask implements Callable<String> {

    @Override
    public String call() throws Exception {

        System.out.println(Thread.currentThread().getName() + " -> Fetching Sales Data...");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + " -> Sales Data Ready");
        return "Sales Report";
    }
}
