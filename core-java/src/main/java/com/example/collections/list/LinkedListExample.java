package com.example.collections.list;

import com.example.collections.Node;

import java.util.LinkedList;
import java.util.Objects;

/** 1) Find middle element of linked list 2) Different operation on linked list */
public class LinkedListExample {
  public static void main(String[] args) {
    findMiddleElement();
  }

  private static void findMiddleElement() {
    /*
     * Creating a new linked list with Nodes
     */
    Node root = new Node(0);
    Node rootTemp = root;
    Node temp;
    for (int i = 1; i < 10; i++) {
      temp = new Node(i);
      root.setNext(temp);
      root = temp;
    }

    Node.print(rootTemp);

    System.out.println();

    Node moveOne = rootTemp;
    Node moveTwo = rootTemp;
    while (Objects.nonNull(moveTwo)) {
      moveOne = moveOne.getNext();
      if (Objects.nonNull(moveTwo.getNext())) {
        moveTwo = moveTwo.getNext().getNext();
      }
    }
    System.out.println("Middle element : " + moveOne);
  }

  private static void lruCache() {}

  private static void operations() {
    LinkedList<Integer> linkedList = new LinkedList<>();

    // remove on empty list throws NoSuchElementException
    // linkedList.remove();

    linkedList.add(1);
    linkedList.add(2);
    linkedList.add(1, 2);
    System.out.println(linkedList);

    linkedList.addFirst(0);
    System.out.println(linkedList);

    linkedList.addLast(3);
    System.out.println(linkedList);

    System.out.println(linkedList.remove());

    System.out.println(linkedList.remove(1));
    System.out.println(linkedList);

    System.out.println(linkedList.isEmpty());
    System.out.println(linkedList.indexOf(2));

    // Retrieves but do not remove the head
    System.out.println(linkedList.peek());
    // Retrieves and remove the head
    System.out.println(linkedList.poll());

    linkedList.clear();
    System.out.println(linkedList);
  }
}
