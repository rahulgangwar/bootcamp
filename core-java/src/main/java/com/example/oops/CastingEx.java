package com.example.oops;

public class CastingEx {
  public static void main(String[] args) {
    new CastingEx().testSomething();
  }

  private void testSomething() {
    upcasting();
    downcasting();
  }

  private void upcasting() {
    System.out.println("CastingExample.upcasting");
    doSomeThing(new Dog());
  }

  /*
   * Upcasting is used to write generic methods
   * Here method accepts variable to type superclass
   * Instance of child class is passed at runtime i.e upcasting
   */
  private void doSomeThing(Animal animal) {
    System.out.println(animal.getClass()); // prints class main.java.jcr.basics.CastingExample$Dog
    animal.talk(); // prints Dog.talk, this additionally have access to Animal's private methods
  }

  private void downcasting() {
    System.out.println("CastingExample.downcasting");
    doSomeThingElse(new Dog());
  }

  private void doSomeThingElse(Animal animal) {
    System.out.println(animal.getClass()); // prints class main.java.jcr.basics.CastingExample$Dog
    /*
     * This statement will fail if someone pass an instance of Cat
     *
     * Animal anim = new Cat();
     * Dog dog = (Dog) anim;
     *
     * Hence instance of should be used for downcasting
     */
    animal.talk();
    Dog dog = (Dog) animal;
    /*
     * Downcasting is used to access specific behaviour of subclass
     * doSomethingOnlyDogCanDo is not found until we downcast animal to dog
     */
    dog.doSomethingOnlyDogCanDo();
    dog.talk(); // prints Dog.talk, this additionally have access to Animal's private methods
  }

  private class Animal {
    public void talk() {
      System.out.println("Animal.talk");
    }
  }

  private class Dog extends Animal {
    @Override
    public void talk() {
      System.out.println("Dog.talk");
    }

    public void doSomethingOnlyDogCanDo() {
      System.out.println("Dog.doSomethingOnlyDogCanDo");
    }
  }

  private class Cat extends Animal {
    @Override
    public void talk() {
      System.out.println("Cat.talk");
    }
  }
}
