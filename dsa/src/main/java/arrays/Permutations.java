package arrays;

import java.util.Arrays;

public class Permutations {
  public static void main(String[] args) {
    findPermutations(new int[] {1, 2, 3});
  }

  public static void findPermutations(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }

    permutations(arr, 0);
  }

  private static void permutations(int[] arr, int currentIndex) {
    // reached end of array
    if (currentIndex == arr.length - 1) {
      System.out.println(Arrays.toString(arr));
    }

    // iterate from current index to end
    for (int i = currentIndex; i < arr.length; i++) {
      swap(arr, currentIndex, i);
      permutations(arr, currentIndex + 1);
      swap(arr, currentIndex, i);
    }
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
