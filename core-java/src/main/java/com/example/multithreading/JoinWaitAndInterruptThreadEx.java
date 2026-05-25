package com.example.multithreading;

import java.util.stream.IntStream;

public class JoinWaitAndInterruptThreadEx {
  public static void main(String[] args) {
    System.out.println("JoinThreadEx.enter : " + Thread.currentThread().getName());

    final Thread mainThread = Thread.currentThread();

    Thread t =
        new Thread(
            () -> {
              IntStream.range(0, 15)
                  .asLongStream()
                  .forEach(
                      it -> {
                        System.out.println(it + " - " + Thread.currentThread().getName());
                        try {
                          Thread.sleep(2000);
                          if (it == 2 || it == 7) {
                            System.out.println("Interrupting " + mainThread.getName());
                            /*
                             * A thread which is in the sleeping or waiting state can be
                             * interrupted with the help of interrupt() method of Thread class.
                             *
                             * This interrupts the main thread which was waiting due to join
                             */
                            mainThread.interrupt();
                          }
                        } catch (InterruptedException e) {
                          e.printStackTrace();
                        }
                      });
            });

    t.start();

    try {
      /*
       * Current thread waits for execution of this thread
       */
      t.join();
    } catch (InterruptedException e) {
      System.out.println("I was interrupted. " + Thread.currentThread().getName());
    }

    final Object lock = new Object();
    synchronized (lock) {
      try {
        System.out.println("Going to wait : " + Thread.currentThread().getName());
        /*
         * Current thread waits until it is interrupted or notified
         */
        lock.wait();
      } catch (InterruptedException e) {
        System.out.println("I was interrupted again !! " + Thread.currentThread().getName());
      }
    }

    System.out.println("JoinThreadEx.resuming : " + Thread.currentThread().getName());

    System.out.println("JoinThreadEx.exit : " + Thread.currentThread().getName());
  }
}
