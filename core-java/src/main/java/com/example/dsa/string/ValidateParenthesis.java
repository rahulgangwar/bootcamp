package com.example.dsa.string;

import java.util.Stack;

public class ValidateParenthesis {

  public static void main(String[] args) {
    System.out.println(new ValidateParenthesis().isValid("(])"));
  }

  public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
      if (c == '(' || c == '{' || c == '[') {
        stack.push(c);
        continue;
      }

      if (stack.isEmpty()) {
        return false;
      }

      if (c == ')' && stack.peek() == '(') {
        stack.pop();
      } else if (c == '}' && stack.peek() == '{') {
        stack.pop();
      } else if (c == ']' && stack.peek() == '[') {
        stack.pop();
      } else {
        return false;
      }
    }

    return stack.isEmpty();
  }
}
