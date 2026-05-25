package recursion;

public class LuckyNumber {
  public static int count = 2;

  public static void main(String[] args) {
    int x = 74;
    if (isLucky(x)) System.out.println(x + " is a lucky no.");
    else System.out.println(x + " is not a lucky no.");
  }

  public static boolean isLucky(int n) {
    if (count > n) return true;
    if (n % count == 0) return false;

    int nextIndex = n - (n / count);
    count++;
    return isLucky(nextIndex);
  }
}
