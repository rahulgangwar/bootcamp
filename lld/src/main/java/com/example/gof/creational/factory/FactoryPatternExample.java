package com.example.gof.creational.factory;

import com.example.gof.creational.factory.core.Notification;
import com.example.gof.creational.factory.core.NotificationFactory;

public class FactoryPatternExample {
    public static void main(String[] args) {
        // Get Email Notification
        Notification emailNotification = NotificationFactory.createNotification("email");
        emailNotification.notifyUser();

        // Get SMS Notification
        Notification smsNotification = NotificationFactory.createNotification("sms");
        smsNotification.notifyUser();
    }
}
