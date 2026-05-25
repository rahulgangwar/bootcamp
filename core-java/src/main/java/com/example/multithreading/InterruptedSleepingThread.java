package com.example.multithreading;

public class InterruptedSleepingThread extends Thread {

    @Override
    public void run() {
        doAPseudoHeavyWeightJob();
    }

    private void doAPseudoHeavyWeightJob() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            System.out.println(i + " " + i * 2);
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread interrupted... Exiting...");
                break;
            } else {
                sleepBabySleep();
            }
        }
    }

    private void sleepBabySleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Interrupted status is cleared on catching the exception
            System.out.println("Interrupted status " + Thread.interrupted());

            // Setting the status again to true
            Thread.currentThread().interrupt();
            // interrupted method also clears the status
//            System.out.println("Interrupted status " + Thread.interrupted());
            // is Interrupted do not clear the status
            System.out.println("Interrupted status " + Thread.currentThread().isInterrupted());
        }
    }
}
