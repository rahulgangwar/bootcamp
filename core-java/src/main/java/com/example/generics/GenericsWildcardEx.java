package com.example.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericsWildcardEx {
  // Unknown wildcard, Anything
  public static void processElementsUnknown(List<?> elements) {
    for (Object o : elements) {
      System.out.println(o);
    }
  }

  // (‘?’), followed by the extends keyword, followed by its upper bound: <? extends A>
  public static void upperBound(List<? extends A> list) {
    for (A a : list) {
      System.out.println(a.hashCode());
    }
  }

  // (‘?’), followed by the super keyword, followed by its lower bound: <? super A>.
  public static void lowerBound(List<? super B> list) {
    for (Object a : list) {
      System.out.println(a.hashCode());
    }
  }

  public static void main(String[] args) {
    // Any class
    List<Map> list1 = new ArrayList<>();
    processElementsUnknown(list1);

    // Using child class of B
    List<B> list2 = new ArrayList<>();
    upperBound(list2);

    // Using super class of A
    List<A> list3 = new ArrayList<>();
    lowerBound(list3);
  }

  private static class SuperClass {}

  private static class A extends SuperClass {}

  private static class B extends A {}

  private static class C extends A {}

  public class Emp {
    int empId;
    String name;
  }
}
