package com.example.dsa.string;

/**
 * https://leetcode.com/problems/longest-common-prefix/
 *
 * <p>Write a function to find the longest common prefix string amongst an array of strings.
 *
 * <p>If there is no common prefix, return an empty string "".
 *
 * <p>
 *
 * <p>
 *
 * <p>Example 1:
 *
 * <p>Input: strs = ["flower","flow","flight"] Output: "fl" Example 2:
 *
 * <p>Input: strs = ["dog","racecar","car"] Output: "" Explanation: There is no common prefix among
 * the input strings.
 */
public class LongestCommonPrefix {
  public static void main(String[] args) {
    System.out.println(
        new LongestCommonPrefix().longestCommonPrefix(new String[] {"rahul", "ra", "r"}));
  }

  public String longestCommonPrefix(String[] strs) {
    boolean isMatch = true;
    int count = 0;
    Character c = null;
    Integer maxCommonLength = 9999999;

    for (String s : strs) {
      if (s.length() < maxCommonLength) {
        maxCommonLength = s.length();
      }
    }

    while (isMatch && count < maxCommonLength) {
      c = null;
      for (String s : strs) {
        if (s.length() == 0) {
          isMatch = false;
          break;
        }

        if (c == null) {
          c = s.charAt(count);
        } else {
          if (!(c == s.charAt(count))) {
            isMatch = false;
            break;
          }
        }
      }
      if (isMatch) {
        count++;
      }
    }

    if (count == 0) {
      return "";
    }

    return strs[0].substring(0, count);
  }
}
