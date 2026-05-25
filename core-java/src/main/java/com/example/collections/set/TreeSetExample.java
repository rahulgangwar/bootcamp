package com.example.collections.set;

import java.util.TreeSet;

/**
 * Internally its a Treemap: new TreeMap<E,Object>() Objects in a TreeSet are stored in a sorted and
 * ascending order. TreeSet is basically implementation of a self-balancing binary search tree like
 * Red-Black Tree. add, remove and search take O(Log n) time
 */
public class TreeSetExample {
  public static void main(String[] args) {
    TreeSet<String> ts1 = new TreeSet<>();

    // Elements are added using add() method
    ts1.add("B");
    ts1.add("A");
    ts1.add("C");

    // Duplicates will not get insert
    ts1.add("C");

    // Elements get stored in default natural
    // Sorting Order(Ascending)
    System.out.println(ts1);
  }
}
