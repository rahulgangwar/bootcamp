package com.example.multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(500);

        Runnable task1 =
                () -> {
                    account.deposit(200, Thread.currentThread().getName());
                    account.withdraw(100, Thread.currentThread().getName());
                };

        Runnable task2 =
                () -> {
                    account.withdraw(300, Thread.currentThread().getName());
                    account.deposit(150, Thread.currentThread().getName());
                };

        Thread t1 = new Thread(task1, "Thread-1");
        Thread t2 = new Thread(task2, "Thread-2");

        t1.start();
        t2.start();
    }

    static class BankAccount {
        private double balance;
        private final ReentrantLock lock = new ReentrantLock(true); // fair lock

        public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }

        public void deposit(double amount, String threadName) {
            lock.lock();
            try {
                System.out.println(threadName + " is depositing " + amount);
                double newBalance = balance + amount;
                Thread.sleep(100); // simulate some delay
                balance = newBalance;
                System.out.println(threadName + " completed deposit. Balance: " + balance);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }

        public void withdraw(double amount, String threadName) {
            lock.lock();
            try {
                System.out.println(threadName + " is trying to withdraw " + amount);
                if (balance >= amount) {
                    double newBalance = balance - amount;
                    Thread.sleep(100); // simulate some delay
                    balance = newBalance;
                    System.out.println(threadName + " completed withdrawal. Balance: " + balance);
                } else {
                    System.out.println(threadName + " - Insufficient funds! Balance: " + balance);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }

        public double getBalance() {
            return balance;
        }
    }
}
