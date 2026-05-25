package com.example.gof.structural.decorator.impl;

import com.example.gof.structural.decorator.core.Notifier;
import com.example.gof.structural.decorator.core.NotifierDecorator;

// Concrete Decorator - SMS Notifier
public class SMSNotifier extends NotifierDecorator {
    public SMSNotifier(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS: " + message);
    }
}



