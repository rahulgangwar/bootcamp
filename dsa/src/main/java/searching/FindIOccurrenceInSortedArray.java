package searching;

public class FindIOccurrenceInSortedArray {

  public static void main(String[] args) {
    System.out.println(findFirst(new int[] {1, 2, 3, 3, 3, 4, 5}, 3));
    System.out.println(findLast(new int[] {1, 2, 3, 3, 3, 4, 5}, 3));
    System.out.println(totalOccurrences(new int[] {1, 2, 3, 3, 3, 4, 5}, 0));
  }

  public static int findFirst(int[] data, int x) {
    int index = BinarySearch.searchIterative(data, x, 0, data.length - 1);

    while (index > 0 && data[index - 1] == x) {
      index--;
    }
    return index;
  }

  public static int findLast(int[] data, int x) {
    int index = BinarySearch.searchIterative(data, x, 0, data.length - 1);

    while (index < data.length - 1 && data[index + 1] == x) {
      index++;
    }
    return index;
  }

  public static int totalOccurrences(int[] data, int x) {
    int index = BinarySearch.searchIterative(data, x, 0, data.length - 1);

    if (index == -1) return 0;

    int low = index;
    int high = index;
    int count = 1;

    while (low > 0 || high > data.length - 1) {
      if (data[low - 1] != x && data[high + 1] != x) {
        break;
      }

      if (low > 0 && data[low - 1] == x) {
        count++;
        low--;
      }
      if (high < data.length - 1 && data[high + 1] == x) {
        count++;
        high++;
      }
    }

    return count;
  }
}
