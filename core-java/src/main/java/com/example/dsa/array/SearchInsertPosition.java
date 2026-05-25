package com.example.dsa.array;

/**
 * Given a sorted array of distinct integers and a target value, return the index if the target is
 * found. If not, return the index where it would be if it were inserted in order.
 *
 * <p>You must write an algorithm with O(log n) runtime complexity.
 *
 * <p>
 *
 * <p>
 *
 * <p>Example 1:
 *
 * <p>Input: nums = [1,3,5,6], target = 5 Output: 2 Example 2:
 *
 * <p>Input: nums = [1,3,5,6], target = 2 Output: 1 Example 3:
 *
 * <p>Input: nums = [1,3,5,6], target = 7 Output: 4
 *
 * <p>Input Arr, Target, Index [1,3,5,6] , 2 , 1
 */
public class SearchInsertPosition {
  public static void main(String[] args) {
    System.out.println(new SearchInsertPosition().searchInsert(new int[] {1, 3, 5, 6}, 2) == 1);
    System.out.println(new SearchInsertPosition().searchInsert(new int[] {1, 3}, 2) == 1);
  }

  public int searchInsert(int[] nums, int target) {
    return match(0, nums.length - 1, nums, target);
  }

  private int match(int start, int end, int[] arr, int x) {
    if (start > end) {
      return start;
    }

    int mid = (end + start) / 2;
    if (arr[mid] == x) {
      return mid;
    } else if (arr[mid] > x) {
      end = mid - 1;
    } else {
      start = mid + 1;
    }
    return match(start, end, arr, x);
  }
}
