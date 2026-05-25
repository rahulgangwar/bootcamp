package com.example.dsa.string;

public class ValidPalindrome {
  public static void main(String[] args) {
    System.out.println(new ValidPalindrome().isPalindrome("A man, a plan, a canal: Panama"));
  }

  public boolean isPalindrome(String s) {
    int i = 0;
    int j = s.length() - 1;

    boolean isPalindrome = true;

    while (i < j) {
      if (!Character.isLetterOrDigit(s.charAt(i))) {
        i++;
      } else if (!Character.isLetterOrDigit(s.charAt(j))) {
        j--;
      } else {
        isPalindrome = (Character.toLowerCase(s.charAt(i)) == (Character.toLowerCase(s.charAt(j))));
        i++;
        j--;
      }

      if (!isPalindrome) {
        break;
      }
    }

    return isPalindrome;
  }
}
