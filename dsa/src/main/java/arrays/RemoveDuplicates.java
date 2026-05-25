package arrays;

import java.util.Arrays;

public class RemoveDuplicates {
  public static void main(String[] args) {
    System.out.println(Arrays.toString(removeDuplicates(new int[] {10, 10, 20, 20, 30, 30})));
  }

  private static int[] removeDuplicates(int[] data) {
    int index = 0;
    int pointer = 0;

    while (index < data.length - 1 || pointer < data.length - 1) {
      if (index < data.length - 1) {
        if (data[pointer] != data[index]) {
          pointer++;
          data[pointer] = data[index];
        }
        index++;
      } else {
        pointer++;
        data[pointer] = -1;
      }
    }

    return data;
  }
}
