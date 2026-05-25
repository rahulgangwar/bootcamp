package com.example.dsa.string;

/**
 * EASY https://leetcode.com/problems/implement-strstr/ Implement strStr().
 *
 * <p>Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of
 * haystack.
 *
 * <p>Clarification:
 *
 * <p>What should we return when needle is an empty string? This is a great question to ask during
 * an interview.
 *
 * <p>For the purpose of this problem, we will return 0 when needle is an empty string. This is
 * consistent to C's strstr() and Java's indexOf().
 *
 * <p>
 *
 * <p>
 *
 * <p>Example 1:
 *
 * <p>Input: haystack = "hello", needle = "ll" Output: 2
 *
 * <p>Example 2:
 *
 * <p>Input: haystack = "aaaaa", needle = "bba" Output: -1
 *
 * <p>Example 3:
 *
 * <p>Input: haystack = "", needle = "" Output: 0
 */
public class StrStr {
  public static void main(String[] args) {
    System.out.println(new StrStr().strStr("abc", "c"));
  }

  public int strStr(String haystack, String needle) {
    if (needle.length() == 0) {
      return 0;
    }

    char start = needle.charAt(0);

    if (haystack.length() == needle.length()) {
      if (haystack.equals(needle)) {
        return 0;
      }
      return -1;
    }

    for (int i = 0; i < haystack.length(); i++) {
      if (haystack.charAt(i) == start) {
        if ((i + needle.length() - 1) < haystack.length()) {
          if (isMatch(haystack, i, i + needle.length() - 1, needle)) {
            return i;
          }
        }
      }
    }

    return -1;
  }

  private boolean isMatch(String target, int begin, int end, String data) {
    int j = 0;
    for (int i = begin; i <= end; i++) {
      if (target.charAt(i) == data.charAt(j)) {
        j++;
      } else {
        return false;
      }
    }

    return j == data.length();
  }
}
