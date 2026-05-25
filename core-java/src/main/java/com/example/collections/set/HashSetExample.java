package com.example.collections.set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * Hashset is internally a hash map which stores elements as key and using PRESENT = new Object() as
 * value
 */
public class HashSetExample {
  public static void main(String[] args) {
    Set<A> set = new java.util.HashSet<>();
    set.add(new A(1));
    set.add(new A(1));
    set.add(new A(2));
    System.out.println("Size : " + set.size());
  }

  @AllArgsConstructor
  @EqualsAndHashCode // Implement equals and hashcode method to avoid duplicate elements in the SET
  private static class A {
    private int id;
  }
}
