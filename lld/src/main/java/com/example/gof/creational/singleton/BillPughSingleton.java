package com.example.gof.creational.singleton;

// This is the most efficient method without synchronization overhead.
public class BillPughSingleton {
    private BillPughSingleton() {}

    // Inner static helper class
    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
