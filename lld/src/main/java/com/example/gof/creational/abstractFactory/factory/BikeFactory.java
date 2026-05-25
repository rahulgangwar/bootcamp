package com.example.gof.creational.abstractFactory.factory;

import com.example.gof.creational.abstractFactory.vehicle.Bike;

public class BikeFactory implements VehicleFactory<Bike> {
    @Override
    public Bike create() {
        return new Bike();
    }
}
