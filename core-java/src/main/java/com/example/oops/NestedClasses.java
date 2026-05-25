package com.example.oops;

public class NestedClasses {
  static int num = 0;
  int sum = 0;

  private static class StaticClass {
    void doSomething() {
      System.out.println(num);
      //      System.out.println(sum); // sum in not accessible as it is non-static
    }
  }

  private class InnerClass {
    void doSomething() {
      System.out.println(num);
      System.out.println(sum); // both static and non-static members are accessible
    }
  }
}
