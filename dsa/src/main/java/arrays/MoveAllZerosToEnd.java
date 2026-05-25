package arrays;

import java.util.Arrays;

public class MoveAllZerosToEnd {
  public static void main(String[] args) {
    System.out.println(Arrays.toString(moveZerosToEnd(new int[] {0, 1, 0, 2, 0, 3})));
  }

  public static int[] moveZerosToEnd(int[] data) {
    int pointer = 0;
    int index = 0;

    while (pointer < data.length || index < data.length) {
      if (index < data.length) {
        if (data[index] != 0) {
          data[pointer] = data[index];
          pointer++;
        }
        index++;
      } else {
        data[pointer] = 0;
        pointer++;
      }
    }
    return data;
  }
}
