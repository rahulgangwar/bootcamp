package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EnumExample {
  public static void main(String[] args) {
    TLight myTlight = TLight.RED;
    System.out.println(myTlight.getDuration());
  }

  enum Direction {
    EAST,
    WEST,
    NORTH,
    SOUTH;
  }

  // enum with member variable, constructor, abstract method
  @AllArgsConstructor
  @Getter
  enum TLight {
    GREEN(30) {
      public TLight next() {
        return RED;
      }
    },
    RED(10) {
      public TLight next() {
        return TLight.GREEN;
      }
    };

    private int duration;

    public abstract TLight next();
  }
}
