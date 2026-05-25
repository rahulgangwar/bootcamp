package com.example.gof.creational.factory.core;

import com.example.gof.creational.factory.impl.EmailNotification;
import com.example.gof.creational.factory.impl.SMSNotification;

// Factory Class
public class NotificationFactory {

    // Factory Method
    public static Notification createNotification(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        return switch (type.toLowerCase()) {
            case "email" -> new EmailNotification();
            case "sms" -> new SMSNotification();
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }
}
