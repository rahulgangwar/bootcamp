package com.example.gof.structural.adapter;

// Adapter
public class PowerAdapter implements USBTypeCCharger {
    private final OldPowerSocket oldPowerSocket;

    public PowerAdapter(OldPowerSocket oldPowerSocket) {
        this.oldPowerSocket = oldPowerSocket;
    }

    @Override
    public void chargePhone() {
        oldPowerSocket.providePower();
        System.out.println("Power adapted to USB-C and phone is charging.");
    }
}

