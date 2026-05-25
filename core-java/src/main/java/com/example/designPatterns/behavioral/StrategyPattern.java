package com.example.designPatterns.behavioral;

import java.util.ArrayList;
import java.util.List;

public class StrategyPattern {

  public static void main(String[] args) {
    ShoppingCart shoppingCart = new ShoppingCart();
    shoppingCart.addItem("item1");
    shoppingCart.addItem("item2");

    shoppingCart.pay(new PaypalStrategy("details"));
    shoppingCart.pay(new CreditCardStrategy("details"));
  }

  private interface PaymentStrategy {
    void pay(int amount);
  }

  private static class CreditCardStrategy implements PaymentStrategy {

    private String details;

    public CreditCardStrategy(String details) {
      this.details = details;
    }

    @Override
    public void pay(int amount) {
      System.out.println(amount + " paid with credit/debit card");
    }
  }

  private static class PaypalStrategy implements PaymentStrategy {

    private String details;

    public PaypalStrategy(String details) {
      this.details = details;
    }

    @Override
    public void pay(int amount) {
      System.out.println(amount + " paid using Paypal.");
    }
  }

  private static class ShoppingCart {

    List<String> items = new ArrayList<String>();

    public ShoppingCart() {
      this.items = new ArrayList();
    }

    public void addItem(String item) {
      this.items.add(item);
    }

    public void removeItem(String item) {
      this.items.remove(item);
    }

    public int calculateTotal() {
      int sum = 0;
      for (String item : items) {
        sum += item.hashCode(); // dummyLogic to get price
      }
      return sum;
    }

    public void pay(PaymentStrategy paymentMethod) {
      int amount = calculateTotal();
      paymentMethod.pay(amount);
    }
  }
}
