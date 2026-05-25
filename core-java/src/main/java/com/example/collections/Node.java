package com.example.collections;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
  private int data;
  private Node prev;
  private Node next;

  public Node(int data) {
    this.data = data;
  }

  public static void print(final Node root) {
    Node temp = root;
    while (temp != null) {
      System.out.print(temp + "->");
      temp = temp.getNext();
    }
    System.out.print(" null");
  }

  @Override
  public String toString() {
    return "(" + data + ")";
  }
}
