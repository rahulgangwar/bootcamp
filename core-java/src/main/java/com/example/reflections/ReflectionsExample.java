package com.example.reflections;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionsExample {
  private int no;

  private ReflectionsExample(int no) {
    this.no = no;
  }

  public static void main(String[] args) {
    try {
      Class classObj = Class.forName("main.java.jcr.basics.ReflectionsExample");
      /*
       * Accepts (MethodName, Optional param types in case of overloaded method)
       */
      Method method = classObj.getMethod("myMethod");
      /*
       * Accepts (instanceOnWhichToInvoke, params)
       */
      method.invoke(new ReflectionsExample(21));
    } catch (ClassNotFoundException
        | NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public static void singletonSerialization() {
    System.out.println(SingletonExp.getInstance());
    System.out.println(SingletonExp.getInstance());

    SingletonExp singletonExp = SingletonExp.getInstance();
    System.out.println(singletonExp);

    try {
      FileOutputStream fos = new FileOutputStream("/home/rahul.babu/text.txt");
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(singletonExp);

      FileInputStream fis = new FileInputStream("/home/rahul.babu/text.txt");
      ObjectInputStream ois = new ObjectInputStream(fis);
      SingletonExp employee1234 = (SingletonExp) ois.readObject();
      System.out.println("Employee : " + employee1234);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void myMethod() {
    System.out.println("ReflectionsExample.myMethod : " + no);
  }

  private static class SingletonExp extends OutputStream implements Serializable {
    private static SingletonExp INSTANCE = new SingletonExp();

    public static SingletonExp getInstance() {
      return INSTANCE;
    }

    @Override
    public void write(int b) throws IOException {}

    /*
     * Prevents creation of multiple instances of singleton throught deserialization
     */
    private Object readResolve() {
      return INSTANCE;
    }
  }
}
