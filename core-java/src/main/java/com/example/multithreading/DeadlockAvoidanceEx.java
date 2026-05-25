package com.example.multithreading;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockAvoidanceEx {
  private final ReentrantLock lock = new ReentrantLock();

  public static void main(String[] args) {
    new DeadlockAvoidanceEx().testThings();
  }

  private void testThings() {

    final Shared s1 = new Shared();
    final Shared s2 = new Shared();

    new Thread(() -> s1.methodOne(s2)).start();
    new Thread(() -> s2.methodTwo(s1)).start();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    long ids[] = bean.findMonitorDeadlockedThreads();

    if (ids != null) {
      for (ThreadInfo t : bean.getThreadInfo(ids)) {
        System.out.println(
            String.format(
                "Deadlocked thread details. ID:%s, Name:%s , LockName:%s, LockOwnerId:%s, LockOwnerName:%s ",
                t.getThreadId(),
                t.getThreadName(),
                t.getLockName(),
                t.getLockOwnerId(),
                t.getLockOwnerName()));
      }
    } else {
      System.out.println("No Deadlocked Threads");
    }
  }

  private String threadName() {
    return Thread.currentThread().getName();
  }

  private class Shared {
    public void methodOne(Shared s) {
      if (lock.tryLock()) {
        try {
          System.out.println(threadName() + "is executing methodOne...");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          System.out.println(threadName() + "is calling methodTwo...");
          s.methodTwo(this);
          System.out.println(threadName() + "is finished executing methodOne...");
        } finally {
          lock.unlock();
        }
      }
    }

    public void methodTwo(Shared s) {
      if (lock.tryLock()) {
        try {
          System.out.println(threadName() + "is executing methodTwo...");
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(threadName() + "is calling methodOne...");
          s.methodOne(this);
          System.out.println(threadName() + "is finished executing methodTwo...");
        } finally {
          lock.unlock();
        }
      }
    }
  }
}
