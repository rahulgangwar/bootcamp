package com.example.gof.structural.bridge;

import com.example.gof.structural.bridge.device.Device;
import com.example.gof.structural.bridge.device.Radio;
import com.example.gof.structural.bridge.device.TV;
import com.example.gof.structural.bridge.remote.AdvancedRemote;
import com.example.gof.structural.bridge.remote.BasicRemote;
import com.example.gof.structural.bridge.remote.RemoteControl;

public class BridgePatternExample {
    public static void main(String[] args) {
        // TV with Basic Remote
        Device tv = new TV();
        RemoteControl basicRemote = new BasicRemote(tv);
        basicRemote.turnOn();
        basicRemote.setVolume(20);
        ((BasicRemote) basicRemote).mute();
        basicRemote.turnOff();

        System.out.println("-------------------");

        // Radio with Advanced Remote
        Device radio = new Radio();
        RemoteControl advancedRemote = new AdvancedRemote(radio);
        advancedRemote.turnOn();
        advancedRemote.setVolume(50);
        ((AdvancedRemote) advancedRemote).setMaxVolume();
        advancedRemote.turnOff();
    }
}
