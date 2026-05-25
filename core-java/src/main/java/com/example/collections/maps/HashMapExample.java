package com.example.collections.maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class HashMapExample {
  public static void main(String[] args) {
    new HashMapExample().doSomething();
  }

  private void doSomething() {
    Map<String, Integer> budget = new HashMap<>();
    budget.put("B", 200);
    budget.put("D", 300);
    budget.put("A", 400);
    budget.put("C", 100);
    System.out.println("map before sorting: " + budget);

    sortByKey(budget);
    sortByValue(budget);
  }

  private void sortByKey(Map<String, Integer> budget) {
    Map<String, Integer> sortedByKey =
        budget.entrySet().stream()
            /*
             * Map's inner interface entry have default method comparingByValue which returns the
             * comparator to do so
             */
            .sorted(Map.Entry.comparingByKey())
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

    System.out.println("map after sorting by key: " + sortedByKey);
  }

  private void sortByValue(Map<String, Integer> budget) {
    Map<String, Integer> sortedByValue =
        budget.entrySet().stream()
            /*
             * Collections.reverseOrder reversed the comparator
             */
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

    System.out.println("map after sorting by value: " + sortedByValue);
  }

  private void synchronizedMaps() {
    // Hash table , every method is synchronized
    Map table = new Hashtable();

    // Synchronized Hash map , each method internally perform synchronization on mutex, i.e an
    // internal shared object
    Map syncMap = Collections.synchronizedMap(new HashMap<>());

    // ConcurrentHashMap, locking is only in case of put and update internally using synchronization
    // on segment
    Map concurrentHashMap = new ConcurrentHashMap<>();
  }

  @Getter
  @Setter
  @AllArgsConstructor
  static class Account {
    private int accountNumber;
    private String holderName;

    // Depends only on account number
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + accountNumber;
      return result;
    }

    // Compare only account numbers
    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Account other = (Account) obj;
      if (accountNumber != other.accountNumber) return false;
      return true;
    }
  }
}
