package matrix;

public class BoundaryTraversal {
  public static void main(String[] args) {
    int[][] data = {
      {1, 2, 3, 4},
      {10, 11, 12, 5},
      {9, 8, 7, 6}
    };

    // only one row
    int[][] data1 = {{1, 2, 3, 4}};
    // only one column
    int[][] data2 = {{1}, {2}, {3}, {4}};

    System.out.println("==========");
    print(data, 3, 4);
    System.out.println("==========");
    print(data1, 1, 4);
    System.out.println("==========");
    print(data2, 4, 1);
  }

  private static void print(int[][] data, int r, int c) {

    if (r == 1) {
      for (int i = 0; i < c; i++) {
        System.out.println(data[0][i]);
      }
      return;
    } else if (c == 1) {
      for (int i = 0; i < r; i++) {
        System.out.println(data[i][0]);
      }
      return;
    }

    // traverse right -- row fixed
    for (int i = 0; i < c; i++) {
      System.out.println(data[0][i]);
    }
    // traverse down - column fixed
    for (int i = 1; i < r; i++) {
      System.out.println(data[i][c - 1]);
    }
    // traverse left -- row fixed
    for (int i = c - 2; i >= 0; i--) {
      System.out.println(data[r - 1][i]);
    }
    // traverse up -- column fixed
    for (int i = r - 2; i >= 1; i--) {
      System.out.println(data[i][0]);
    }
  }
}
