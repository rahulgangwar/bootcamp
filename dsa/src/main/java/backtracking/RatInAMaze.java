package backtracking;

public class RatInAMaze {
  public static int totalRecursions = 0;

  public static void main(String[] args) {
    int[][] maze = {
      {1, 1, 0, 0},
      {1, 1, 1, 0},
      {0, 1, 1, 1}
    };

    int[][] path = {
      {0, 0, 0, 0},
      {0, 0, 0, 0},
      {0, 0, 0, 0}
    };

    int rows = 3;
    int cols = 4;

    // print path
    System.out.println("Path found : " + move(maze, 0, 0, rows, cols, path));
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(path[i][j] + ", ");
      }
      System.out.println("");
    }

    System.out.println("Total recursions : " + totalRecursions);
  }

  public static boolean move(int[][] maze, int i, int j, int k, int l, int[][] path) {
    totalRecursions++;
    if (i == k - 1 && j == l - 1) {
      path[i][j] = 1;
      return true;
    }
    if (i >= k || j >= l) return false;
    if (maze[i][j] == 0) {
      return false;
    }

    path[i][j] = 1;
    boolean solutionFound = move(maze, i + 1, j, k, l, path);
    if (!solutionFound) {
      solutionFound = move(maze, i, j + 1, k, l, path);
    }

    if (!solutionFound) path[i][j] = 0;

    return solutionFound;
  }
}
