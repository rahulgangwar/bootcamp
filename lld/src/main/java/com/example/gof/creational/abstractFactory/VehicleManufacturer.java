package com.example.gof.creational.abstractFactory;

import com.example.gof.creational.abstractFactory.factory.VehicleFactory;
import com.example.gof.creational.abstractFactory.vehicle.Vehicle;

public class VehicleManufacturer {

    public Vehicle manufactureVehicle(VehicleFactory<? extends Vehicle> vehicleFactory) {
        return vehicleFactory.create();
    }
}
