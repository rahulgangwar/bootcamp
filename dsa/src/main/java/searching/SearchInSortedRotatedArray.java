package searching;

import java.util.HashSet;
import java.util.Set;

public class SearchInSortedRotatedArray {
  public static void main(String[] args) {
    //    System.out.println(find(new int[] {6, 7, 8, 1, 2, 3, 4, 5}, 10));

    Set<Integer> ints = new HashSet<>();
    ints.remove(1);
    System.out.println("done");
  }

  public static int find(int[] data, int x) {
    int low = 0;
    int high = data.length - 1;

    while (low <= high) {
      int mid = (low + high) / 2;

      if (data[mid] == x) return mid;

      // check if first half is sorted
      if (data[low] < data[mid]) {
        // if first element is less than x than discard this half
        if (data[low] < x && data[mid] > x) {
          high = mid - 1;
        } else {
          low = mid + 1;
        }
      } else {
        if (data[high] > x && data[mid] < x) {
          low = mid + 1;
        } else {
          high = mid - 1;
        }
      }
    }
    return -1;
  }
}
