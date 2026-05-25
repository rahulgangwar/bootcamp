package com.example.collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListExample {
  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 10, 5, 2, 3, 4, 5));

    System.out.println(list);
    /*
     * Removes the element at the index
     */
    list.remove(2);

    System.out.println(list);
    Integer integer = 2;
    /*
     * Removes the first occurrence of the specified element from this list
     */
    list.remove(integer);
    System.out.println(list);
    list.remove(integer);
    System.out.println(list);
  }
}
