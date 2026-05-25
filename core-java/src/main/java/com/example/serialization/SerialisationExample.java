package com.example.serialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;

public class SerialisationExample {
  private final String filename = "/Users/rahgangw1/code/text.ser";

  public static void main(String[] args) throws IOException {
    new SerialisationExample().parentChildExample();
  }

  private void parentChildExample() {
    writeObject(new Employee("123", "password", "rahul"));
    Employee employee = readObject();
    System.out.println("Deserialized Employee : " + employee);
  }

  public void writeObject(Employee employee) {
    try {
      System.out.println("Serialising : " + employee);
      FileOutputStream fos = new FileOutputStream(filename);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(employee);
      oos.close();
      System.out.println("Writing to file complete");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Employee readObject() {
    Employee employee = null;
    try {
      FileInputStream fis = new FileInputStream(filename);
      ObjectInputStream ois = new ObjectInputStream(fis);
      employee = (Employee) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return employee;
  }

  // =============== Case when parent class is not serializable but child class is
  // =========================
  // todo 1: For inherited variables the default values will be set example name would be null
  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  /*
   * Rule 1: If parent class is serializable, child class by default become serializable
   *
   * Rule 2: If this is not serializable
   * Child class will use the default value of the inherited properties during serialization
   * ex: null for name
   *
   * NOTE: If we do not want to make this serializable but still want to write its properties
   * Then child class should override writeObject and readObject methods
   *
   * Rule 3: In case of inheritance : either parent class should be serializable or
   * it should have default constructor
   * else exception : java.io.InvalidClassException:no valid constructor.
   */
  static class Person {
    private String name;
  }

  @Setter
  @Getter
  /*
   * If this contains instance of Another class which is not serializable
   * exception: NotSerializableException
   */
  static class Employee extends Person implements Serializable {

    private static final long SerialVersionUID = 10L;
    private static String company;
    private String empId;
    /*
     * Transient variables are not serialized
     */
    private transient String password;

    Employee(String empId, String password, String name) {
      super(name);
      this.empId = empId;
      this.password = password;
      company = "Sapient";
    }

    /** Implement this method to do something custumized during serialization */
    private void writeObject(ObjectOutputStream oos) throws IOException {
      System.out.println("Employee.writeObject");
      oos.defaultWriteObject();
      //            oos.writeObject(getName());
    }

    /** Implement this method to do something customized during deserialization */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      System.out.println("Employee.readObject");
      ois.defaultReadObject();
      //            setName((String) ois.readObject());
    }

    @Override
    public String toString() {
      return "Employee{"
          + "name='"
          + super.getName()
          + '\''
          + ", empId='"
          + empId
          + '\''
          + ", password='"
          + password
          + '\''
          + '}';
    }
  }
}
