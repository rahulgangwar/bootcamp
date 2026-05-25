package com.example.exception;

/** 1) Class not found exception 2) No class def error */
public class ClassExceptions {
  public static void main(String[] args) {
    classNotFound();
  }

  private static void classNotFound() {
    try {
      Class.forName("myclass");
    } catch (final ClassNotFoundException cnf) {
      System.out.println("Exception: " + cnf);
    }
  }

  private static void noClassDefError() {
    /*
     * Example of this is lets say class A is using instance of B
     * but at compile time class file of B is missing
     */
  }
}
