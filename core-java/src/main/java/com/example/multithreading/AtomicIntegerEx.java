package com.example.multithreading;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerEx {
  private CountDownLatch latch = new CountDownLatch(5);
  private AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) {
    new AtomicIntegerEx().testThings();
  }

  private void testThings() {
    IntStream.range(0, 5)
        .asLongStream()
        .forEach(
            (it) -> {
              new ProcessingThread().start();
            });
    try {
      latch.await();
    } catch (InterruptedException e) {
      System.out.println("I am interrupted");
    }
    System.out.println("Total thread completed : " + count);
  }

  @Getter
  private class ProcessingThread extends Thread {

    @Override
    public void run() {
      System.out.println("Starting : " + Thread.currentThread().getName());
      doWork();
      count.getAndIncrement();
      latch.countDown();
    }

    private void doWork() {
      IntStream.range(0, 2)
          .asLongStream()
          .forEach(
              (it) -> {
                try {
                  System.out.println("Working: " + it + " - " + Thread.currentThread().getName());
                  Thread.sleep(1000);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              });
    }
  }
}
