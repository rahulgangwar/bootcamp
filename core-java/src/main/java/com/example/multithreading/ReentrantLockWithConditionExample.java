package com.example.multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ReentrantLockWithConditionExample {
    public static void main(String[] args) {
        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(3);

        Runnable producer =
                () -> {
                    try {
                        for (int i = 1; i <= 5; i++) {
                            buffer.put(i);
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                };

        Runnable consumer =
                () -> {
                    try {
                        for (int i = 1; i <= 5; i++) {
                            buffer.take();
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                };

        Thread p1 = new Thread(producer, "Producer-1");
        Thread c1 = new Thread(consumer, "Consumer-1");

        p1.start();
        c1.start();
    }

    static class BoundedBuffer<T> {
        private final Queue<T> queue = new LinkedList<>();
        private final int capacity;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        public BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        public void put(T item) throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() == capacity) {
                    System.out.println(Thread.currentThread().getName() + " waiting - buffer full");
                    notFull.await(); // wait until space available
                }
                queue.add(item);
                System.out.println(Thread.currentThread().getName() + " produced: " + item);
                notEmpty.signal(); // signal a waiting consumer
            } finally {
                lock.unlock();
            }
        }

        public T take() throws InterruptedException {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    System.out.println(
                            Thread.currentThread().getName() + " waiting - buffer empty");
                    notEmpty.await(); // wait until item available
                }
                T item = queue.poll();
                System.out.println(Thread.currentThread().getName() + " consumed: " + item);
                notFull.signal(); // signal a waiting producer
                return item;
            } finally {
                lock.unlock();
            }
        }
    }
}
