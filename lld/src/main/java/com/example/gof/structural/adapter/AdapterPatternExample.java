package com.example.gof.structural.adapter;

public class AdapterPatternExample {
    public static void main(String[] args) {
        // Incompatible power socket
        OldPowerSocket oldSocket = new OldPowerSocket();

        // Adapter converts old socket's power to USB-C
        USBTypeCCharger charger = new PowerAdapter(oldSocket);

        // Charging phone through the adapter
        charger.chargePhone();
    }
}

