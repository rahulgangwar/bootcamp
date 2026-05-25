package com.example.gof.creational.prototype;

public class PrototypeExample {
    public static void main(String[] args) throws CloneNotSupportedException {
        Employees employees = new Employees();
        employees.loadData();
        System.out.println(employees);

        // avoid loading data again by cloning
        Employees employees1 = (Employees) employees.clone();
        System.out.println(employees1);
    }
}
