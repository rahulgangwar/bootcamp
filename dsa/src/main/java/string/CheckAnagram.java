package string;

import java.util.Arrays;

public class CheckAnagram {
  static final int size = 256;

  public static void main(String[] args) {
    System.out.println(areAnagram("rahul", "ahulr"));
  }

  // naive approach
  private static boolean isAnagram(String s1, String s2) {
    char[] arr1 = s1.toCharArray();
    char[] arr2 = s2.toCharArray();
    Arrays.sort(arr1);
    Arrays.sort(arr2);
    return Arrays.equals(arr1, arr2);
  }

  // optimized approach
  static boolean areAnagram(String s1, String s2) {
    if (s1.length() != s2.length()) return false;

    int[] count = new int[size];
    for (int i = 0; i < s1.length(); i++) {
      System.out.println(s1.charAt(i));
      count[s1.charAt(i)]++;
      count[s2.charAt(i)]--;
    }

    for (int i = 0; i < size; i++) {
      if (count[i] != 0) return false;
    }
    return true;
  }
}
