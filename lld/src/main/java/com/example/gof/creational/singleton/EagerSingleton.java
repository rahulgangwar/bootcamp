package com.example.gof.creational.singleton;

public class EagerSingleton {
    // Instance is created at the time of class loading
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // Private constructor to prevent instantiation
    private EagerSingleton() {}

    // Global access method
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
