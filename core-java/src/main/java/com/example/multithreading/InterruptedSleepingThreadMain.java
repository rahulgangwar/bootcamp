package com.example.multithreading;

/**
 * 1) If this thread is blocked in an invocation of wait, sleep or join its interrupt status will be
 * cleared and it will receive an {@link InterruptedException} hence we set the status again with
 * Thread.currentThread().interrupt()
 */
public class InterruptedSleepingThreadMain {
  public static void main(String[] args) {
    InterruptedSleepingThread thread = new InterruptedSleepingThread();
    thread.start();
    try {
      Thread.sleep(5000);
      thread.interrupt();
    } catch (InterruptedException e) {
      System.out.println("is interrupted : " + Thread.currentThread().isInterrupted());
      Thread.currentThread().interrupt();
      System.out.println("is interrupted : " + Thread.currentThread().isInterrupted());
    }
  }
}
