package searching;

public class SquareRoot {
  public static void main(String[] args) {
    System.out.println(findSquareRoot(8));
  }

  public static int findSquareRoot(int x) {
    int low = 0;
    int high = x;
    int ans = 0;
    int mid;

    while (low <= high) {
      mid = (low + high) / 2;
      int midSqr = mid * mid;

      if (midSqr > x) {
        high = mid - 1;
      } else if (midSqr < x) {
        ans = mid;
        low = mid + 1;
      } else {
        ans = mid;
        break;
      }
    }

    return ans;
  }
}
