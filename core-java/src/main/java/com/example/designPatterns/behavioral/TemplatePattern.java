package com.example.designPatterns.behavioral;

public class TemplatePattern {

  public static void main(String[] args) {
    // using template method
    HouseTemplate houseType = new WoodenHouse();
    houseType.buildHouse();

    System.out.println("************");

    houseType = new GlassHouse();
    houseType.buildHouse();
  }

  private abstract static class HouseTemplate {
    // template method, final so subclasses can't override
    public final void buildHouse() {
      buildFoundation();
      buildPillars();
      buildWalls();
      System.out.println("House is built.");
    }

    private void buildFoundation() {
      System.out.println("Building foundation with cement,iron rods and sand");
    }

    // methods to be implemented by subclasses
    public abstract void buildWalls();

    public abstract void buildPillars();
  }

  private static class WoodenHouse extends HouseTemplate {
    @Override
    public void buildWalls() {
      System.out.println("Building Wooden Walls");
    }

    @Override
    public void buildPillars() {
      System.out.println("Building Pillars with Wood coating");
    }
  }

  private static class GlassHouse extends HouseTemplate {
    @Override
    public void buildWalls() {
      System.out.println("Building Glass Walls");
    }

    @Override
    public void buildPillars() {
      System.out.println("Building Pillars with glass coating");
    }
  }
}
