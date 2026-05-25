package arrays;

public class MaxSubarrayCircularSum {
  public static void main(String[] args) {
    traverse(new int[] {1, 2, 3, 4, 5, 6}, 3);
  }

  public static void traverse(int[] data, int index) {
    for (int i = 0; i < data.length; i++) {
      System.out.println(data[(i + index) % data.length]);
    }
  }
}
