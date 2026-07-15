package com.example.multithreading.executors.tasks;

import java.util.concurrent.Callable;

public class EmployeeTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " -> Fetching Employee Data...");
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " -> Employee Data Ready");
        return "Employee Report";
    }
}
