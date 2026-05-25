package com.example.designPatterns.creational;

/*
 * Prototype design pattern is used when the Object creation is a costly affair
 * and requires a lot of time and resources and you have a similar object already existing.
 *
 * Suppose we have an Object that loads data from database.
 * Now we need to modify this data in our program multiple times,
 * so it’s not a good idea to create the Object using new keyword and load all the data again from database.
 * The better approach would be to clone the existing object into a new object and then do the data manipulation.
 */
public class PrototypePattern {
  public static void main(String[] args) throws CloneNotSupportedException {
    Employee employee = new Employee("rahul", new Passport(1));
    System.out.println(employee);
    Employee clonedEmployee = employee.clone();
    System.out.println(clonedEmployee);
  }

  private static class Passport implements Cloneable {
    private final int id;

    Passport(int id) {
      this.id = id;
    }

    @Override
    public Passport clone() throws CloneNotSupportedException {
      return (Passport) super.clone();
    }
  }

  private static class Employee implements Cloneable {
    private final String name;
    private final Passport passport;

    Employee(String name, Passport passport) {
      this.name = name;
      this.passport = passport;
    }

    @Override
    public Employee clone() throws CloneNotSupportedException {
      return new Employee(this.name, this.passport.clone());
    }

    public String toString() {
      return name + ":" + passport;
    }
  }
}
