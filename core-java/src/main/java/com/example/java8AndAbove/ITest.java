package com.example.java8AndAbove;

public interface ITest {
    // Default method
    default void defaultMethod() {
        log("Default method called");
    }

    // Static method
    static void staticMethod() {
        logStatic("Static method called");
    }

    // Private method
    private void log(String message) {
        System.out.println("Log: " + message);
    }

    // Private static method
    private static void logStatic(String message) {
        System.out.println("Static Log: " + message);
    }
}
