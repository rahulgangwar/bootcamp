package com.example.java8;

/** Created by rahul on 25/11/17. */
public class MethodReference {
  public static void main(String[] args) {
    refToStaticMethod();
  }

  // type 1: Reference to a static method.
  private static void refToStaticMethod() {
    Sayable sayable = Myclass::myMethod;
    sayable.say(1);
  }

  // type 2: Reference to an Instance Method
  public static void refToInstanceMethod() {
    Sayable sayable = new Myclass(1)::mySecondMethod;
    sayable.say(1);
  }

  // type 3: Reference to a Constructor
  public static void refToConstructor() {
    Sayable sayable = Myclass::new;
    sayable.say(1);
  }

  interface Sayable {
    void say(int i);
  }

  static class Myclass {
    Myclass(int i) {
      System.out.println("Inside constructor : " + i);
    }

    static void myMethod(int i) {
      System.out.println("Inside my method : " + i);
    }

    void mySecondMethod(int i) {
      System.out.println("Inside mySecondMethod : " + i);
    }
  }
}
