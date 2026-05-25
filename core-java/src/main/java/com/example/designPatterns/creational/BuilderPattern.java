package com.example.designPatterns.creational;

/*
1> First of all you need to create a static nested class and then copy all the arguments from the outer class to the Builder class. We should follow the naming convention and if the class name is Computer then builder class should be named as ComputerBuilder.
2> Java Builder class should have a public constructor with all the required attributes as parameters.
3> Java Builder class should have methods to set the optional parameters and it should return the same Builder object after setting the optional attribute.
4> The final step is to provide a build() method in the builder class that will return the Object needed by client program. For this we need to have a private constructor in the Class with Builder class as argument.*/
public class BuilderPattern {

  public static void main(String[] args) {
    // setting middle name is only optional here
    System.out.println(new User.UserBuilder("rahul").middleName("babu").build());
  }

  private static class User {
    // mandatory
    private String firstName;

    // optional
    private String middleName;

    User(UserBuilder userBuilder) {
      this.firstName = userBuilder.firstName;
      this.middleName = userBuilder.middleName;
    }

    @Override
    public String toString() {
      return "User [firstName=" + firstName + ", middleName=" + middleName + "]";
    }

    static class UserBuilder {
      // mandatory
      private String firstName;

      // optional
      private String middleName;

      public UserBuilder(String firstname) {
        this.firstName = firstname;
      }

      public UserBuilder middleName(String middleName) {
        this.middleName = middleName;
        return this;
      }

      public User build() {
        return new User(this);
      }
    }
  }
}
