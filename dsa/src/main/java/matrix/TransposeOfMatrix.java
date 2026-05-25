package matrix;

import java.util.Arrays;

public class TransposeOfMatrix {
  public static void main(String[] args) {
    int[][] data = {
      {1, 2, 3, 4},
      {12, 13, 14, 5},
      {11, 16, 15, 6},
      {10, 9, 8, 7}
    };
    transpose(data, 4);
    System.out.println(Arrays.deepToString(data));
  }

  private static void transpose(int[][] data, int n) {
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        swap(data, i, j);
      }
    }
  }

  private static void swap(int[][] data, int i, int j) {
    int temp = data[i][j];
    data[i][j] = data[j][i];
    data[j][i] = temp;
  }
}
