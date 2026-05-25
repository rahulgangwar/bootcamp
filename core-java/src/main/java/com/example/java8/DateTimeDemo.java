package com.example.java8;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class DateTimeDemo {
  public static void main(String[] args) {
    dateOperations();
  }

  public static void dateOperations() {
    LocalDate date = LocalDate.now();
    System.out.println("Today's date : " + date);

    LocalTime time = LocalTime.now();
    System.out.println("Current time : " + time);

    LocalTime timeWithTimeZone = LocalTime.now(ZoneId.of("UCT"));
    System.out.println("Current time for Zone UCT: " + timeWithTimeZone);

    Instant instant = Instant.now();
    System.out.println("Current instant : " + instant);
  }
}
