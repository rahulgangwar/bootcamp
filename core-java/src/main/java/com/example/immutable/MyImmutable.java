package com.example.immutable;

// declare class as final, so it cannot be extended
public final class MyImmutable {
  // make the variables as private and final
  private final String name;

  private final DateCreated dateCreated;

  public MyImmutable(String name, DateCreated dateCreated) {
    this.name = name;
    // mutable objects should be cloned in constructors
    this.dateCreated = new DateCreated(dateCreated.getDate());
  }

  // do not expose setter methods

  public String getName() {
    return name;
  }

  // getter of mutable property should return clone
  public DateCreated getDateCreated() {
    return new DateCreated(dateCreated.getDate());
  }
}
