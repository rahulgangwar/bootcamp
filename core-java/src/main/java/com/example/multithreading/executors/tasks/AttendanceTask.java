package com.example.multithreading.executors.tasks;

import java.util.concurrent.Callable;

public class AttendanceTask implements Callable<String> {

    @Override
    public String call() throws Exception {

        System.out.println(Thread.currentThread().getName() + " -> Fetching Attendance Data...");
        // Simulate database/API call
        Thread.sleep(1500);
        System.out.println(Thread.currentThread().getName() + " -> Attendance Data Ready");
        return "Attendance Report";
    }
}
