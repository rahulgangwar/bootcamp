package com.example.gof.creational.abstractFactory;

import com.example.gof.creational.abstractFactory.factory.BikeFactory;
import com.example.gof.creational.abstractFactory.factory.CarFactory;
import com.example.gof.creational.abstractFactory.factory.VehicleFactory;
import com.example.gof.creational.abstractFactory.vehicle.Bike;
import com.example.gof.creational.abstractFactory.vehicle.Car;

public class AbstractFactoryExample {
    public static void main(String[] args) {
        VehicleFactory<Car> carFactory = new CarFactory();
        VehicleFactory<Bike> bikeFactory = new BikeFactory();

        System.out.println(new VehicleManufacturer().manufactureVehicle(carFactory));
        System.out.println(new VehicleManufacturer().manufactureVehicle(bikeFactory));

    }
}
