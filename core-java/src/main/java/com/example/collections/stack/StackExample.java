package com.example.collections.stack;

import java.util.Stack;

public class StackExample {
  public static void main(String[] args) {
    Stack<Integer> stack = new Stack<>();

    // poping and empty stack throws EmptyStackException
    //        stack.pop();

    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    stack.push(5);
    System.out.println(stack);

    System.out.println(stack.peek());
    System.out.println(stack.pop());
    System.out.println(stack.isEmpty());
    System.out.println(stack.contains(1));
    System.out.println(stack);
    System.out.println(stack.empty());
    System.out.println(stack);
    System.out.println(stack.search(2));
    System.out.println(stack.search(4));
    System.out.println(stack.search(5));
  }
}
