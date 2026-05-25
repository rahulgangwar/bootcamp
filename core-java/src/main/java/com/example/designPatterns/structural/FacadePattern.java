package com.example.designPatterns.structural;

/*Provide a unified interface to a set of interfaces in a subsystem.
Facade Pattern defines a higher-level interface that makes the subsystem easier to use.**/
public class FacadePattern {
  public static void main(String[] args) {
    ShapeMaker shapeMaker = new ShapeMaker();
    shapeMaker.drawCircle();
    shapeMaker.drawRectangle();
    shapeMaker.drawSquare();
  }

  private interface Shape {
    void draw();
  }

  private static class Rectangle implements Shape {
    @Override
    public void draw() {
      System.out.println("Rectangle::draw()");
    }
  }

  private static class Square implements Shape {
    @Override
    public void draw() {
      System.out.println("Square::draw()");
    }
  }

  private static class Circle implements Shape {
    @Override
    public void draw() {
      System.out.println("Circle::draw()");
    }
  }

  private static class ShapeMaker {
    private Shape circle;
    private Shape rectangle;
    private Shape square;

    public ShapeMaker() {
      circle = new Circle();
      rectangle = new Rectangle();
      square = new Square();
    }

    public void drawCircle() {
      circle.draw();
    }

    public void drawRectangle() {
      rectangle.draw();
    }

    public void drawSquare() {
      square.draw();
    }
  }
}
