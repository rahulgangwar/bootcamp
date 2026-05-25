package com.example.libraries;

public class RxJavaExample {
  public static void main(String[] args) {
    for (int i = 0; i < 100000; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(i);
    }
  }
}
