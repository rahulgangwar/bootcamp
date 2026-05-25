package com.example.multithreading;

public class ThreadLocalEx {
  public static void main(String[] args) {
    MyRunnable sharedRunnableInstance = new MyRunnable();

    Thread thread1 = new Thread(sharedRunnableInstance);
    Thread thread2 = new Thread(sharedRunnableInstance);

    thread1.start();
    thread2.start();

    try {
      thread1.join(); // wait for thread 1 to terminate
      thread2.join(); // wait for thread 2 to terminate
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static class MyRunnable implements Runnable {

    // Used to store thread data
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public void run() {
      String threadDetails =
          String.format(
              "Thread > Name:%s, Id:%s",
              Thread.currentThread().getName(), Thread.currentThread().getId());
      System.out.println("Running : " + threadDetails);
      threadLocal.set(threadDetails);

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        System.out.println("I am interrupted");
      }

      System.out.println("Completed : " + threadLocal.get());
    }
  }
}
