package com.example.multithreading;

public class VolatileEx {
  // try without boolean to see the difference
  static volatile boolean execute = true;
  private String otherThread;

  public static void main(String[] args) {
    new VolatileEx().testThings();
  }

  private void testThings() {
    new Thread1().start();
    new Thread2().start();
  }

  private class Thread1 extends Thread {

    @Override
    public void run() {
      System.out.println("Starting: " + Thread.currentThread().getName());
      int i = 0;
      while (i < 20) {
        System.out.println("Executing :" + Thread.currentThread().getName());
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (i == 5) {
          System.out.println("Stopping: " + otherThread);
          execute = false;
        }
        if (i == 10) {
          System.out.println("Starting: " + otherThread);
          execute = true;
        }
        i++;
      }
    }
  }

  private class Thread2 extends Thread {

    @Override
    public void run() {
      otherThread = Thread.currentThread().getName();
      System.out.println("Starting: " + Thread.currentThread().getName());
      while (true) {
        if (execute) {
          System.out.println("Executing :" + Thread.currentThread().getName());
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
