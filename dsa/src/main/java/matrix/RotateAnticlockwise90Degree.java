package matrix;

// to rotate by 90 degree anticlockwise, first we need to transpose and than swap rows
public class RotateAnticlockwise90Degree {
  public static void main(String[] args) {
    int[][] data = {
      {1, 2, 3, 4},
      {12, 13, 14, 5},
      {11, 16, 15, 6},
      {10, 9, 8, 7}
    };
    transpose(data, 4);
    swapRows(data, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        System.out.print(data[i][j] + " ");
      }
      System.out.println();
    }
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

  private static void swapRows(int[][] data, int n) {
    int i = 0;
    int j = n - 1;

    while (i < j) {
      for (int k = 0; k < n; k++) {
        int temp = data[i][k];
        data[i][k] = data[j][k];
        data[j][k] = temp;
      }
      i++;
      j--;
    }
  }
}
