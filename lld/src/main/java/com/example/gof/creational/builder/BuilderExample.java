package com.example.gof.creational.builder;

public class BuilderExample {
    public static void main(String[] args) {
        Computer computer = new Computer.ComputerBuilder("2GB", "500GB")
                .setBluetoothEnabled(true)
                .setGraphicsCardEnabled(true)
                .build();
        System.out.println(computer);
    }
}
