package matrix;

// todo try again
public class SpiralMatrix {
  public static void main(String[] args) {
    int[][] data = {
      {1, 2, 3, 4},
      {12, 13, 14, 5},
      {11, 16, 15, 6},
      {10, 9, 8, 7}
    };
    print(data, 4, 4);

    int[][] data2 = {
      {1, 2, 3, 4},
      {8, 7, 6, 5}
    };
    System.out.println("");
    print(data2, 2, 4);

    int[][] data3 = {{1, 2, 3, 4}};
    System.out.println("");
    print(data3, 1, 4);

    int[][] data4 = {{1}, {2}, {3}, {4}};
    System.out.println("");
    print(data4, 4, 1);
  }

  private static void print(int[][] data, int r, int c) {

    int top = 0;
    int right = c - 1;
    int bottom = r - 1;
    int left = 0;

    while (top <= bottom && left <= right) {
      for (int i = left; i <= right; i++) {
        System.out.print(data[top][i] + " ");
      }
      top++;
      for (int i = top; i <= bottom; i++) {
        System.out.print(data[i][right] + " ");
      }
      right--;
      if (top <= bottom) {
        for (int i = right; i >= left; i--) {
          System.out.print(data[bottom][i] + " ");
        }
        bottom--;
      }

      if (left <= right) {
        for (int i = bottom; i >= top; i--) {
          System.out.print(data[i][left] + " ");
        }
        left++;
      }
    }
  }
}
