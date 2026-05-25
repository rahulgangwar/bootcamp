package com.example;

public class PrimitiveDataTypes {
  public static void main(String[] args) {

    // ----------------------------- byte, short, int and long
    // ----------------------------------------------

    // creating byte type
    byte myByte = 127; // width is 8 bits

    // creating short type
    short myShort = 12777; // width is 16 bits

    // creating int type
    int myInt = 123456; // width is 32 bits

    // creating long type
    long myLong = 100067L; // width is 64 bits

    long calculatedLong = 50000L + (10 * (myByte + myShort + myInt));

    System.out.println("calculatedLong >> " + calculatedLong);

    // ----------------------------- float and double ----------------------------------------------

    float myFloat = (float) 5.6; // width is 32 bits

    double myDouble = 5.6223423; // width is 64 bits

    System.out.println(myDouble);

    double kgEquivalentOfOnePound = 0.45359237d;

    double givenPound = 200d;

    double equivalentKg = givenPound * kgEquivalentOfOnePound;

    System.out.println("Kg equivalent of " + givenPound + " pounds = " + equivalentKg);

    // ----------------------------- char and boolean ----------------------------------------------

    char unicodeChar = '\u00AE'; // unicode char start with \ u  // width is 16 bits
    System.out.println("Unicode char: " + unicodeChar);

    boolean myBoolean = true; // width is 1 bit
  }
}
