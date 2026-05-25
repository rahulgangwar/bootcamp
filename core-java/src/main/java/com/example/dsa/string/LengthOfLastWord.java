package com.example.dsa.string;

public class LengthOfLastWord {
  public static void main(String[] args) {
    System.out.println(new LengthOfLastWord().lengthOfLastWordWithoutSplit("Rahul Babu cat"));
  }

  public int lengthOfLastWord(String s) {
    String[] str = s.split(" ");
    return str[str.length - 1].length();
  }

  public int lengthOfLastWordWithoutSplit(String s) {
    boolean wasPrevCharSpace = false;
    int currentLength = 0;
    for (char c : s.toCharArray()) {
      if (c != ' ') {
        if (wasPrevCharSpace) {
          currentLength = 0;
        }
        currentLength++;
        wasPrevCharSpace = false;
      }

      if (c == ' ') {
        wasPrevCharSpace = true;
      }
    }
    return currentLength;
  }
}
