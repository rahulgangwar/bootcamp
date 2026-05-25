package com.example.immutable;

import java.util.Date;

public class ImmutableMain {
  public static void main(String[] args) {
    DateCreated dateCreated = new DateCreated(new Date());
    System.out.println("DateCreated : " + dateCreated.getDate());

    MyImmutable myImmutable = new MyImmutable("rahul", dateCreated);
    System.out.println(myImmutable.getDateCreated().getDate().getTime());
    System.out.println(
        "Initial Date created of immutable : " + myImmutable.getDateCreated().getDate());
    myImmutable.getDateCreated().getDate().setTime(1000000L);
    System.out.println(myImmutable.getDateCreated().getDate().getTime());

    sleep();

    /* Here if the date created object is modified then the property of myImmutable is also modified
     * Hence during constructor clone of mutable should be used instead of passed object **/
    dateCreated.setDate(new Date());
    System.out.println("New date of Date created after Modifying : " + dateCreated.getDate());
    System.out.println("New date of My immutable: " + myImmutable.getDateCreated().getDate());
    System.out.println(myImmutable.getDateCreated().getDate().getTime());

    /*If the getter return the original object of mutable property, it can be modified
     * Hence the getter should only return a clone*/
    DateCreated dateCreatedNew = myImmutable.getDateCreated();
    sleep();
    dateCreatedNew.setDate(new Date());
    System.out.println("Final New date of My immutable: " + myImmutable.getDateCreated().getDate());
  }

  static void sleep() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
