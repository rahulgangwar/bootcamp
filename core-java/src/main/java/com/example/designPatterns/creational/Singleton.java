package com.example.designPatterns.creational;

public class Singleton {
  // volatile variable
  private static volatile Singleton instance;

  private static Singleton getInstance() {
    System.out.println("1 - " + threadName() + " - " + instance);
    if (instance == null) {
      System.out.println("2 - " + threadName() + " - " + instance);
      synchronized (Singleton.class) {
        if (instance == null) { // if we do not check here 2 threads will get different objects
          instance = new Singleton();
          System.out.println("3 - " + threadName() + " - " + instance);
        }
      }
    }
    System.out.println("4 - " + threadName() + " - " + instance);
    return instance;
  }

  public static void main(String[] args) {
    Thread thread1 =
        new Thread(() -> System.out.println(threadName() + " - " + Singleton.getInstance()));
    Thread thread2 =
        new Thread(() -> System.out.println(threadName() + " - " + Singleton.getInstance()));

    thread1.start();
    thread2.start();
  }

  private static String threadName() {
    return Thread.currentThread().getName();
  }
}
