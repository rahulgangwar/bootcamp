package com.example.designPatterns.creational;

public class AbstractFactoryPattern {
  public static void main(String[] args) {
    System.out.println("Computer : " + ComputerFactory.makeComputer(new SuperComputerFactory()));
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

  private abstract static class AbstractFactory {
    public abstract Computer make();
  }

  private static class SuperComputerFactory extends AbstractFactory {
    public Computer make() {
      return new SuperComputer();
    }
  }

  private static class ComputerFactory {
    public static Computer makeComputer(AbstractFactory factory) {
      return factory.make();
    }
  }
}
