package com.example.multithreading.executors.tasks;

public class EmailTask implements Runnable {

    private final String pdfFile;

    public EmailTask(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " -> Sending Email : " + pdfFile);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " -> Email Sent");
    }
}
