package com.example.gof.structural.bridge.device;


// Concrete Implementor 2
public class Radio implements Device {
    private int volume = 0;

    @Override
    public void turnOn() {
        System.out.println("Radio is now ON.");
    }

    @Override
    public void turnOff() {
        System.out.println("Radio is now OFF.");
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("Radio volume set to: " + this.volume);
    }
}
