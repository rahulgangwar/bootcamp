package com.example.gof.structural.decorator.core;

import com.example.gof.structural.decorator.core.Notifier;

// Decorator
public abstract class NotifierDecorator implements Notifier {
    protected Notifier notifier;

    public NotifierDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void send(String message) {
        notifier.send(message);
    }
}
