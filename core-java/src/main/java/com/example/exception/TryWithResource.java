package com.example.exception;

import java.io.IOException;

public class TryWithResource {

  public static void main(String[] args) {
    new TryWithResource().doSomething();
  }

  private void doSomething() {
    try (MyResource myResource = new MyResource()) {
      myResource.doIt();
    } catch (IOException e) {
      System.out.println("IOException");
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("Exception : " + e.toString());

      int suppressedCount = e.getSuppressed().length;
      for (int i = 0; i < suppressedCount; i++) {
        System.out.println("Suppressed: " + e.getSuppressed()[i]);
      }
    }
    System.out.println("Outside try");
  }

  private static class MyResource implements AutoCloseable {
    MyResource() throws IOException {}

    void doIt() {
      System.out.println("MyResource.doIt");
      throw new NullPointerException();
    }

    @Override
    public void close() throws Exception {
      System.out.println("MyResource.close");
      /*
       * Java suppress any exception which might occur while closing resource
       */
      throw new ArithmeticException();
    }
  }
}
