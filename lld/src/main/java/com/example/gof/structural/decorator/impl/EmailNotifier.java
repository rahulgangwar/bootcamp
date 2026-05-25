package com.example.gof.structural.decorator.impl;

import com.example.gof.structural.decorator.core.Notifier;

// Concrete Component - Basic Email Notifier
public class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}
