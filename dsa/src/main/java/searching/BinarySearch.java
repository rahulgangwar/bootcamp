package searching;

public class BinarySearch {
  public static void main(String[] args) {
    int[] data = {1, 2, 3, 4, 5, 6, 7};

    searchRecursive(data, 2, 0, data.length - 1);
    searchIterative(data, 2, 0, data.length - 1);
  }

  public static int searchRecursive(int[] data, int x, int low, int high) {
    if (low > high) {
      System.out.println("Not found!");
      return -1;
    }

    int mid = (high + low) / 2;
    if (data[mid] == x) {
      System.out.println("Found at : " + mid);
      return mid;
    }
    if (data[mid] > x) {
      return searchRecursive(data, x, low, mid - 1);
    } else {
      return searchRecursive(data, x, mid + 1, high);
    }
  }

  public static int searchIterative(int[] data, int x, int low, int high) {
    while (low <= high) {
      int mid = (high + low) / 2;
      if (data[mid] == x) {
        System.out.println("Found at : " + mid);
        return mid;
      }
      if (data[mid] > x) {
        high = mid - 1;
      } else {
        low = mid + 1;
      }
    }
    System.out.println("Not found!");
    return -1;
  }
}
