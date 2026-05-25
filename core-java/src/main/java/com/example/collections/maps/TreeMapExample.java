package com.example.collections.maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapExample {
  public static void main(String[] args) {
    passComparator();
  }

  private static void defaultSorting() {
    /*
     * If  comparator is not passed in constructor
     * Treemap while putting the value try to cast key to Comparable
     * If key is not comparable runtime exception is encountered
     */
    Map<Car, String> treeMap = new TreeMap<>();
    treeMap.put(new Car("B", 30), "audi");
    treeMap.put(new Car("C", 10), "maruti");
    treeMap.put(new Car("A", 20), "bmw");

    System.out.println("========map sorted by speed=========");
    treeMap.forEach((K, V) -> System.out.println(K + " : " + V));
  }

  private static void passComparator() {
    TreeMap<Car, String> treeMap = new TreeMap<>(Comparator.comparing(o -> o.name));

    treeMap.put(new Car("B", 30), "audi");
    treeMap.put(new Car("C", 10), "maruti");
    treeMap.put(new Car("A", 20), "bmw");

    System.out.println("========map sorted by name=========");
    /*
     * forEach of map accepts BiConsumer
     */
    treeMap.forEach((key, value) -> System.out.println(key));
  }

  @Getter
  @Setter
  @ToString
  @AllArgsConstructor
  /*
   * If a class do not implements comparable and is used in Treemap and no comparator is passed in constructor
   * java.lang.ClassCastException: main.java.jcr.collections.TreeMapExample$Car cannot be cast to java.lang.Comparable
   */
  private static class Car implements Comparable<Car> {
    private String name;
    private int speed;

    @Override
    public int compareTo(Car car) {
      if (this.speed == car.speed) {
        return 0;
      } else {
        return this.speed > car.speed ? -1 : 1;
      }
    }
  }
}
