package com.example.designPatterns.creational;

public class FactoryPattern {
  public static void main(String[] args) {
    Computer computer = ComputerFactory.make(ComputerType.SUPER_COMPUTER);
    System.out.println("Got computer: " + computer);
  }

  private static enum ComputerType {
    HOME_PC,
    SUPER_COMPUTER;
  }

  private abstract static class Computer {
    int hardDisk;
    int ram;

    public int getHardDisk() {
      return hardDisk;
    }

    public int getRam() {
      return ram;
    }
  }

  private static class SuperComputer extends Computer {
    SuperComputer() {
      hardDisk = 100;
      ram = 4;
    }
  }

  private static class ComputerFactory {
    public static Computer make(ComputerType type) {
      Computer computer = null;
      switch (type) {
        case SUPER_COMPUTER:
          computer = new SuperComputer();

          // will have case for other type of computers
      }
      return computer;
    }
  }
}
