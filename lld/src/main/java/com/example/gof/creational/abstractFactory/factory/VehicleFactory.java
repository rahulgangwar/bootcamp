package com.example.gof.creational.abstractFactory.factory;

import com.example.gof.creational.abstractFactory.vehicle.Vehicle;

public interface VehicleFactory<T extends Vehicle> {
    T create();
}
