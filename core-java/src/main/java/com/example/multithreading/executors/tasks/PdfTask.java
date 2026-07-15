package com.example.multithreading.executors.tasks;

import java.util.concurrent.Callable;

public class PdfTask implements Callable<String> {

    private final String employeeData;
    private final String salesData;
    private final String attendanceData;

    public PdfTask(String employeeData, String salesData, String attendanceData) {

        this.employeeData = employeeData;
        this.salesData = salesData;
        this.attendanceData = attendanceData;
    }

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " -> Generating PDF...");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " -> PDF Generated");
        return "MonthlyReport.pdf";
    }
}
