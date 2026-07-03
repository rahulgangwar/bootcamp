package com.example.gof.structural.decorator;

import com.example.gof.structural.decorator.core.Notifier;
import com.example.gof.structural.decorator.impl.EmailNotifier;
import com.example.gof.structural.decorator.impl.PushNotifier;
import com.example.gof.structural.decorator.impl.SMSNotifier;

/**
 * The Decorator Pattern is a structural design pattern that dynamically adds responsibilities or behavior to an object
 * by wrapping it inside decorator objects. It follows the Open/Closed Principle because new functionality can be added
 * without modifying existing classes. A classic Java example is the InputStream hierarchy, where BufferedInputStream
 * and DataInputStream decorate FileInputStream to add buffering and data-reading capabilities
 */
public class DecoratorPatternExample {
    public static void main(String[] args) {
        String message = "Your order has been shipped!";

        // Basic Email Notification
        Notifier emailNotifier = new EmailNotifier();
        System.out.println("=== Email Only ===");
        emailNotifier.send(message);

        // Email + SMS Notification
        Notifier smsEmailNotifier = new SMSNotifier(new EmailNotifier());
        System.out.println("\n=== Email + SMS ===");
        smsEmailNotifier.send(message);

        // Email + Push Notification
        Notifier pushEmailNotifier = new PushNotifier(new EmailNotifier());
        System.out.println("\n=== Email + Push ===");
        pushEmailNotifier.send(message);

        // Email + SMS + Push Notification
        Notifier allNotifier = new PushNotifier(new SMSNotifier(new EmailNotifier()));
        System.out.println("\n=== Email + SMS + Push ===");
        allNotifier.send(message);
    }
}
