package com.example.collections.list;

import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
  public static void main(String[] args) {
    Queue<Integer> queue = new LinkedList<>();

    System.out.println(queue.peek()); // returns null if queue is empty
    System.out.println(queue.poll()); // returns null if queue is empty

    queue.add(1);
    queue.add(2);
    queue.add(3);
    queue.add(4);

    System.out.println(queue.isEmpty());
    System.out.println(queue.peek());
    System.out.println(queue.poll());
    System.out.println(queue);
  }
}
