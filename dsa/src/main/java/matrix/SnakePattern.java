package matrix;

public class SnakePattern {
  public static void main(String[] args) {
    int[][] arr = {
      {1, 2, 3, 4},
      {8, 7, 6, 5},
      {9, 10, 11, 12}
    };
    print(arr);
  }

  private static void print(int[][] data) {
    for (int i = 0; i < data.length; i++) {
      if (i % 2 == 0) {
        for (int j = 0; j < data[i].length; j++) {
          System.out.println(data[i][j]);
        }
      } else {
        for (int j = data[i].length - 1; j >= 0; j--) {
          System.out.println(data[i][j]);
        }
      }
    }
  }
}
