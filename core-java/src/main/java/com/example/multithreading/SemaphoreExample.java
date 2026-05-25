package com.example.multithreading;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    static class DBConnection {

        private final Semaphore semaphore;

        public DBConnection(int connectionPool) {
            semaphore = new Semaphore(connectionPool);
        }

        boolean connect() {
            return semaphore.tryAcquire();
        }

        void disconnect() {
            semaphore.release();
        }

        int availableConnections() {
            return semaphore.availablePermits();
        }
    }
}
