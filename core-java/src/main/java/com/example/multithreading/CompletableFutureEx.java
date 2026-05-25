package com.example.multithreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletableFutureEx {
  static AtomicInteger orderNo = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    System.out.println("=============== Starting processing =================");
    int totalOrders = 3;
    CountDownLatch latch = new CountDownLatch(totalOrders);
    for (int i = 0; i < totalOrders; i++) {
      /*
       * supplyAsync: Returns a new CompletableFuture that is asynchronously completed
       * by a task running in the {@link ForkJoinPool#commonPool()} with
       * the value obtained by calling the given Supplier.
       *
       * Note: optionally we can pass Executor to use our thread pool
       */
      CompletableFuture.supplyAsync(Order::newOrder)
          .thenApply(order -> order.step1(order))
          .thenApply(order -> order.step2(order))
          .thenAccept(
              order -> {
                latch.countDown();
                order.complete(order);
              });
    }
    latch.await();
    System.out.println("================= Completed ==============================");
  }

  public static void sleep() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static class Order {
    int id;

    Order(int id) {
      System.out.println("Creating new order with id : " + id);
      sleep();
      this.id = id;
    }

    static Order newOrder() {
      return new Order(orderNo.incrementAndGet());
    }

    @Override
    public String toString() {
      return "Order{" + "id=" + id + '}';
    }

    Order step1(Order order) {
      System.out.println(order + " > Step 1");
      sleep();
      return order;
    }

    Order step2(Order order) {
      System.out.println(order + " > Step 2");
      sleep();
      return order;
    }

    void complete(Order order) {
      System.out.println(order + " > Complete");
      sleep();
    }
  }
}
