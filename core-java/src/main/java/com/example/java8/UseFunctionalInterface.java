package com.example.java8;

public class UseFunctionalInterface {

  public static void main(String[] args) {
    FunctionalInterfaceDemo functionalInterfaceDemo =
        () -> System.out.println("Inside FunctionalInterfaceDemo");
    functionalInterfaceDemo.doSomething();

    FunctionalInterfaceExample fix =
        i -> {
          System.out.println("Integer : " + i);
          return i + 1;
        };

    Integer result = fix.printIntegerAndReturnIncrementalValue(1);
    System.out.println("Result: " + result);
  }

  @FunctionalInterface
  interface FunctionalInterfaceExample {
    Integer printIntegerAndReturnIncrementalValue(Integer i);
  }

  // Annotation is used since it give compilation error when there are multiple abstract methods
  // A functionalInterface is an interface which allows only one abstract method, which is the
  // condition to form and lambda exp.
  @FunctionalInterface
  interface FunctionalInterfaceDemo {
    public void doSomething();

    // declaring multiple abstract methods give compilation errors
    //    public void doSomethingElse();

  }
}
