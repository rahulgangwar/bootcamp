package com.example.scenarios.ParkingLot.vehicle;

import lombok.Getter;

@Getter
public abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }
}
