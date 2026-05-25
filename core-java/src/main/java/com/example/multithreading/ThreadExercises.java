package com.example.multithreading;

public class ThreadExercises {

  public static void main(String[] args) {
    exercise1();
  }

  // What would be the output
  private static void exercise1() {
    class A implements Runnable {
      private int count;

      @Override
      public synchronized void run() {
        System.out.println("Run");
        for (int i = 0; i < 50; i++) {
          count += i;
        }
        System.out.println("Notify");
        notify();
      }
    }

    A a = new A();
    new Thread(a).start();

    synchronized (a) {
      System.out.println("Waiting");
      try {
        a.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(a.count);
    }
  }
}
