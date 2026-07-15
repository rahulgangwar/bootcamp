package com.example.multithreading.executors;

import com.example.multithreading.executors.tasks.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ReportGenerationService {

    private final ExecutorService executor;

    public ReportGenerationService(ExecutorService executor) {
        this.executor = executor;
    }

    public void generateMonthlyReport() {
        try {
            System.out.println("\nStarting Monthly Report Generation...\n");

            // Submit all independent tasks
            Future<String> employeeFuture = executor.submit(new EmployeeTask());
            Future<String> salesFuture = executor.submit(new SalesTask());
            Future<String> attendanceFuture = executor.submit(new AttendanceTask());

            System.out.println("Main thread is free to do other work...\n");

            // Wait for results
            String employeeData = employeeFuture.get(5, TimeUnit.SECONDS);
            String salesData = salesFuture.get(5, TimeUnit.SECONDS);
            String attendanceData = attendanceFuture.get(5, TimeUnit.SECONDS);

            // Generate PDF
            Future<String> pdfFuture =
                    executor.submit(new PdfTask(employeeData, salesData, attendanceData));
            String pdfPath = pdfFuture.get();

            // Fire-and-forget email
            executor.execute(new EmailTask(pdfPath));
            System.out.println("\nReport Generation Completed Successfully");
        } catch (Exception ex) {
            System.out.println("Report Generation Failed");
            ex.printStackTrace();
        }
    }
}
