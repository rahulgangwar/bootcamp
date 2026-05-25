package com.example.gof.structural.bridge.device;

// Implementor
public interface Device {
    void turnOn();
    void turnOff();
    void setVolume(int volume);
}
