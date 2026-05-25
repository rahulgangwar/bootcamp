package matrix;

// rows are sorted and columns are sorted
public class SearchInSortedMatrix {
  public static void main(String[] args) {
    int[][] data = {
      {10, 20, 30, 40},
      {15, 25, 35, 45},
      {27, 29, 35, 45},
      {32, 33, 39, 50}
    };

    search(data, 4, 29);
  }

  private static void search(int[][] data, int n, int x) {
    int r = 0;
    int c = n - 1;

    boolean isFound = false;
    while (r <= n - 1 && c >= 0) {
      int curr = data[r][c];
      if (curr == x) {
        isFound = true;
        System.out.println("Found at " + r + "," + c);
        break;
      } else if (curr > x) {
        c--;
      } else {
        r++;
      }
    }
    if (!isFound) {
      System.out.println("Not found !!");
    }
  }
}
