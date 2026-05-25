package arrays;

public class MaximumSumSubArrayKadanesAlgo {
  public static void main(String[] args) {
    System.out.println(maxSumSubArray(new int[] {2, 3, -8, 7, -1, 2, 3}));
    System.out.println(maxSumSubArrayCircular(new int[] {8, -4, 3, -5, 4}));
    System.out.println(maxSumSubArrayCircularOptimised(new int[] {8, -4, 3, -5, 4}));
  }

  private static int sumOfArray(int[] data) {
    int sum = 0;

    for (int datum : data) {
      sum += datum;
    }
    return sum;
  }

  private static int maxSumSubArray(int[] data) {
    int sum = data[0];

    for (int i = 1; i < data.length; i++) {
      if (sum + data[i] > data[i]) {
        sum += data[i];
      } else {
        sum = data[i];
      }
    }
    return sum;
  }

  private static int minSumSubArray(int[] data) {
    int sum = data[0];

    for (int i = 1; i < data.length; i++) {
      if (sum + data[i] < data[i]) {
        sum += data[i];
      } else {
        sum = data[i];
      }
    }
    return sum;
  }

  private static int maxSumSubArrayCircular(int[] data) {
    int maxSum = 0;
    for (int i = 1; i < data.length; i++) {
      maxSum = Math.max(maxSum, traverse(data, i));
    }

    return maxSum;
  }

  public static int traverse(int[] data, int start) {
    int sum = data[start];

    for (int i = 1; i < data.length; i++) {
      int index = (start + i) % data.length;
      if (sum + data[index] > data[index]) {
        sum += data[index];
      } else {
        sum = data[index];
      }
    }
    return sum;
  }

  private static int maxSumSubArrayCircularOptimised(int[] data) {
    return sumOfArray(data) - minSumSubArray(data);
  }
}
