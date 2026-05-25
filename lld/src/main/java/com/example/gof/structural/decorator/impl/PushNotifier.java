package com.example.gof.structural.decorator.impl;

import com.example.gof.structural.decorator.core.Notifier;
import com.example.gof.structural.decorator.core.NotifierDecorator;

// Concrete Decorator - Push Notifier
public class PushNotifier extends NotifierDecorator {
    public PushNotifier(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending Push Notification: " + message);
    }
}