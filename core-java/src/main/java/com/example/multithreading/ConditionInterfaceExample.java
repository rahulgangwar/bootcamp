package com.example.multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionInterfaceExample {

  public static void main(String[] args) throws InterruptedException {
    ItemQueue itemQueue = new ItemQueue();

    // Create a producer and a consumer.
    Thread producer = new Producer(itemQueue);
    Thread consumer = new Consumer(itemQueue);

    // Start both threads.
    producer.start();
    consumer.start();

    // Wait for both threads to terminate.
    producer.join();
    consumer.join();
  }

  public static class ItemQueue {

    Queue<String> queue = new LinkedList<>();
    int CAPACITY = 5;

    ReentrantLock lock = new ReentrantLock();
    Condition stackEmptyCondition = lock.newCondition();
    Condition stackFullCondition = lock.newCondition();

    public void add(String item) {
      try {
        lock.lock();
        while (queue.size() == CAPACITY) {
          stackFullCondition.await();
        }
        System.out.println(Thread.currentThread().getName() + "| Adding : " + item);
        queue.add(item);
        stackEmptyCondition.signalAll();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }

    public String remove() {
      try {
        lock.lock();
        while (queue.size() == 0) {
          stackEmptyCondition.await();
        }

      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        stackFullCondition.signalAll();
        lock.unlock();
      }
      String item = queue.remove();
      System.out.println(Thread.currentThread().getName() + "| Removing : " + item);
      return item;
    }

    boolean isEmpty() {
      return queue.isEmpty();
    }
  }

  static class Producer extends Thread {
    private final ItemQueue queue;

    public Producer(ItemQueue queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

      try {
        for (String number : numbers) {
          queue.add(number);
          Thread.sleep(2000);
        }
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }

  static class Consumer extends Thread {
    private final ItemQueue queue;

    public Consumer(ItemQueue queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      try {
        do {
          Object number = queue.remove();
          Thread.sleep(5000);

          if (number == null) {
            return;
          }
        } while (!queue.isEmpty());
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }
}
