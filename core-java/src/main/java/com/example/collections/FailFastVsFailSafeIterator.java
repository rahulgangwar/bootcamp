package com.example.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FailFastVsFailSafeIterator {
  public static void main(String[] args) {
    Map map = new HashMap();
    map.put("name", "rahul");
    map.put("age", 29);

    Iterator iterator = map.keySet().iterator();
    System.out.println("---- Iterating on FailFast --------");
    while (iterator.hasNext()) {
      // todo uncommenting below line gives concurrentModificationException
      //            map.put("city", "noida");
      System.out.println(iterator.next());
    }

    Map concurrentHashMap = new ConcurrentHashMap(map);
    /*
     * Returns fail safe iterator which iterates over copy of map
     */
    Iterator iteratorForConcurrentMap = concurrentHashMap.keySet().iterator();

    System.out.println("Values before modification: " + concurrentHashMap.keySet());
    System.out.println("---- Iterating on FailSafe --------");
    while (iteratorForConcurrentMap.hasNext()) {
      iteratorForConcurrentMap.next();
      concurrentHashMap.put("newKey", "newValue");
    }
    System.out.println("Values after modification: " + concurrentHashMap.keySet());
  }
}
