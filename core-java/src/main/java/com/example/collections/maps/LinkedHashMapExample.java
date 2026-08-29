package com.example.collections.maps;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapExample {
    public static void main(String[] args){

        Map<Integer, String> map = new LinkedHashMap<>();

        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        map.put(4, "Four");

        System.out.println("LinkedHashMap: " + map);

        // Accessing elements
        System.out.println("Value for key 2: " + map.get(2));

        // Iterating over the LinkedHashMap
        System.out.println("Iterating over LinkedHashMap:");
        for (java.util.Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}
