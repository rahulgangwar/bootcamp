package com.example.gof.structural.bridge.device;

// Concrete Implementor 1
public class TV implements Device {
    private int volume = 0;

    @Override
    public void turnOn() {
        System.out.println("TV is now ON.");
    }

    @Override
    public void turnOff() {
        System.out.println("TV is now OFF.");
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("TV volume set to: " + this.volume);
    }
}
