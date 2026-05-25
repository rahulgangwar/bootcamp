package com.example.designPatterns.structural;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AdaptorPattern {
  public static void main(String[] args) {
    // Employee adapter is a class which enables use to use employee object where Customer object
    // was supported
    System.out.println(
        CardDesigner.designCard(new EmployeeAdapter(new Employee("rahul", "babu", "sec 73"))));
  }

  public interface Customer {
    String getName();

    String getAddress();
  }

  public static class CardDesigner {
    public static String designCard(Customer customer) {
      return String.format("Name: %s \nAddress: %s", customer.getName(), customer.getAddress());
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Employee {
    private String firstName;
    private String lastName;
    private String officeAddress;
  }

  // 2 way class adapter
  public static class Employee2WayAdapter extends Employee implements Customer {

    @Override
    public String getName() {
      return this.getFirstName() + " " + this.getLastName();
    }

    @Override
    public String getAddress() {
      return this.getOfficeAddress();
    }
  }

  // object adapter
  public static class EmployeeAdapter implements Customer {

    private final Employee adaptee;

    public EmployeeAdapter(Employee employee) {
      this.adaptee = employee;
    }

    @Override
    public String getName() {
      return adaptee.getFirstName() + " " + adaptee.getLastName();
    }

    @Override
    public String getAddress() {
      return adaptee.getOfficeAddress();
    }
  }
}
