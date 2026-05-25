package com.example.multithreading;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerEx {
  private static final Queue integers = new LinkedList();
  private static final int maxCapacity = 10;

  static class Producer implements Runnable {
    @Override
    public void run() {
      while (true) {
        synchronized (integers) {
          try {
            if (integers.size() < maxCapacity) {
              int data = LocalDateTime.now().getSecond();
              integers.add(data);
              System.out.println("Producing : " + data + " - " + Thread.currentThread().getName());
              integers.notifyAll();
            } else {
              System.out.println(
                  "Queue is full : Sleeping thread : " + Thread.currentThread().getName());
            }

            integers.wait();
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            System.out.println("InterruptedException : " + Thread.currentThread().getName());
            e.printStackTrace();
          }
        }
      }
    }
  }

  static class Consumer implements Runnable {
    public static void main(String[] args) {
      new Thread(new Producer()).start();
      new Thread(new Producer()).start();
      new Thread(new Consumer()).start();
    }

    @Override
    public void run() {
      while (true) {
        synchronized (integers) {
          try {
            if (!integers.isEmpty()) {
              System.out.println(
                  "Consuming : " + integers.poll() + " - " + Thread.currentThread().getName());
              integers.notifyAll();
            } else {
              System.out.println(
                  "Queue is empty : Sleeping thread : " + Thread.currentThread().getName());
            }

            integers.wait();
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            System.out.println("InterruptedException : " + Thread.currentThread().getName());
            e.printStackTrace();
          }
        }
      }
    }
  }
}
