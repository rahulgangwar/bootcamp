package com.example.multithreading;

import java.util.stream.IntStream;

public class ThreadVsRunnableEx {
  public static void main(String[] args) {
    new ThreadVsRunnableEx().testThings();
  }

  private void doWork() {
    IntStream.range(0, 5)
        .asLongStream()
        .forEach(
            it -> {
              System.out.println(it + "-" + Thread.currentThread().getName());
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
  }

  private void testThings() {
    new FirstThread().start();
    /*
     * Thread is an implementation of Runnable
     * Thread have 2 constructors
     *
     * Default: which sets the target to null
     * for default we need to override the run method and
     * tell what to do else nothing is done due to below impl
     *
     * @Override
     * public void run() {
     *  if (target != null) {
     *      target.run();
     *      }
     *  }
     *
     * Accepting Runnable: which sets the target to runnable
     */
    new Thread(new SecondThread()).start();
  }

  private class FirstThread extends Thread {
    @Override
    public void run() {
      doWork();
    }
  }

  private class SecondThread implements Runnable {
    @Override
    public void run() {
      doWork();
    }
  }
}
