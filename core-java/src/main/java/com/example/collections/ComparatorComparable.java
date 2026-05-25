package com.example.collections;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 1) Checks sorting on uncomparable class 2) Checks sorting on uncomparable class using comparator
 * 3) Checks sorting of a hybrid list
 */
public class ComparatorComparable {
  public static void main(String[] args) {
    //        sortListOfNonComparables();
    sortListOfNonComparablesUsingComparator();
    //        sortHybridList();
  }

  private static void sortListOfNonComparables() {
    System.out.println("ComparatorComparable.sortListOfNonComparables");
    try {
      List list =
          Arrays.asList(
              new NonComparableEmployee(1, "rahul", 32), new NonComparableEmployee(1, "babu", 38));

      System.out.println("Unsorted list : " + list);
      Collections.sort(list); // throws ClassCastException
      System.out.println("Sorted list : " + list);
    } catch (final ClassCastException cce) {
      System.out.println("Exception : " + cce.getMessage());
    }
  }

  private static void sortListOfNonComparablesUsingComparator() {
    System.out.println("ComparatorComparable.sortListOfNonComparablesUsingComparator");
    try {
      List list =
          Arrays.asList(
              new NonComparableEmployee(2, "rahul", 32),
              new NonComparableEmployee(1, "babu", 38),
              new NonComparableEmployee(3, "gangwar", 30),
              new NonComparableEmployee(3, "gangwar", 30),
              new NonComparableEmployee(3, "gangwar", 30),
              new NonComparableEmployee(3, "gangwar", 30),
              new NonComparableEmployee(5, "gangwar", 30),
              new NonComparableEmployee(3, "gangwar", 30),
              new NonComparableEmployee(3, "gangwar", 30),
              new NonComparableEmployee(10, "gangwar", 30));

      System.out.println("Unsorted list : " + list);
      Collections.sort(list, new RollNoComparator());
      System.out.println("Sorted list : " + list);
    } catch (final ClassCastException cce) {
      System.out.println("Exception : " + cce.getMessage());
    }
  }

  private static void sortHybridList() {
    System.out.println("ComparatorComparable.sortHybridList");
    try {
      List hybridList = Arrays.asList(new Student(1, "rahul", 32), new Employee(1, "babu", 38));

      System.out.println("Unsorted list : " + hybridList);
      Collections.sort(hybridList); // throws ClassCastException
      System.out.println("Sorted list : " + hybridList);
    } catch (ClassCastException cce) {
      System.out.println("Exception : " + cce.getMessage());
    }
  }

  @NoArgsConstructor
  @AllArgsConstructor
  private static class Person {
    int rollNo;
    String name;
    int age;

    @Override
    public String toString() {
      return String.format("%s: %s-%s", this.rollNo, this.name, this.age);
    }
  }

  private static class Student extends Person implements Comparable<Student> {
    Student(int rollNo, String name, int age) {
      super(rollNo, name, age);
    }

    @Override
    public int compareTo(Student s2) {
      return Integer.compare(this.age, s2.age);
    }
  }

  private static class Employee extends Person implements Comparable<Employee> {
    Employee(int rollNo, String name, int age) {
      super(rollNo, name, age);
    }

    @Override
    public int compareTo(Employee employee) {
      return Integer.compare(this.age, employee.age);
    }
  }

  private static class NonComparableEmployee extends Person {
    NonComparableEmployee(int rollNo, String name, int age) {
      super(rollNo, name, age);
    }
  }

  private static class RollNoComparator implements Comparator<NonComparableEmployee> {
    @Override
    public int compare(NonComparableEmployee s1, NonComparableEmployee s2) {
      return Integer.compare(s1.rollNo, s2.rollNo);
    }
  }
}
