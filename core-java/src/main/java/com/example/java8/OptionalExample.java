package com.example.java8;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalExample {
  public static void main(String[] args) {
    Integer integer = null;
    Optional<Integer> optional;

    // ========= of vs ofNullable
    try {
      optional = Optional.of(integer); // throws null pointer exception
    } catch (NullPointerException npe) {
      System.out.println("Null pointer exception");
      optional = Optional.ofNullable(integer); // do not throw nullpointer
    }

    // ========== isPresent, get and orElse
    System.out.println(optional.isPresent());
    System.out.println(optional.isPresent());
    Integer integer2 = optional.orElse(2); // perform if value was null

    try {
      System.out.println("Is element present : " + optional.isPresent());
      System.out.println(
          optional.get()); // get on blank throws java.util.NoSuchElementException: No value present
    } catch (NoSuchElementException nsee) {
      System.out.println("No such element : " + nsee.getMessage());
    }
    System.out.println(integer2);

    // =========== use of if present
    Optional optionalNew = Optional.of("Rahul");
    optionalNew.ifPresent(System.out::println); // to do operations if value is present
  }
}
