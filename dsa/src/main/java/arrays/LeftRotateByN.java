package arrays;

import java.util.Arrays;

public class LeftRotateByN {
  public static void main(String[] args) {
    System.out.println(Arrays.toString(leftRotate(new int[] {1, 2, 3, 4, 5}, 2)));
  }

  public static int[] leftRotate(int[] data, int n) {
    int[] temp = new int[n];

    System.arraycopy(data, 0, temp, 0, n);

    int index = 0;
    for (int i = n; i < data.length; i++) {
      data[index++] = data[i];
    }

    System.arraycopy(temp, 0, data, data.length - n, n);

    return data;
  }
}
