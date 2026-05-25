package com.example.scenarios.ParkingLot;

import com.example.scenarios.ParkingLot.space.Level;
import com.example.scenarios.ParkingLot.space.ParkingLot;
import com.example.scenarios.ParkingLot.vehicle.Car;
import com.example.scenarios.ParkingLot.vehicle.Motorcycle;
import com.example.scenarios.ParkingLot.vehicle.Vehicle;

public class ParkingLotDemo {
    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstance();
        parkingLot.addLevel(new Level(1, 100));
        parkingLot.addLevel(new Level(2, 80));

        Vehicle car = new Car("ABC123");
        Vehicle motorcycle = new Motorcycle("M1234");

        // Park vehicles
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(motorcycle);

        // Display availability
        parkingLot.displayAvailability();

        // Unpark vehicle
        parkingLot.unparkVehicle(motorcycle);

        // Display updated availability
        parkingLot.displayAvailability();
    }
}
