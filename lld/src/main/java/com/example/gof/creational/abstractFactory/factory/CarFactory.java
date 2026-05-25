package com.example.gof.creational.abstractFactory.factory;

import com.example.gof.creational.abstractFactory.vehicle.Car;

public class CarFactory implements VehicleFactory<Car> {
    @Override
    public Car create() {
        return new Car();
    }
}
