package com.example.gof.creational.factory.impl;


import com.example.gof.creational.factory.core.Notification;

// Concrete Product: SMS Notification
public class SMSNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an SMS Notification");
    }
}