package recursion;

/** Print the sum of N natural numbers using recursion */
public class SumOfNNaturalNumbers {
  public static void main(String[] args) {
    System.out.println(getSum(4));
  }

  private static int getSum(int n) {
    return calculateSum(n);
  }

  private static int calculateSum(int n) {
    if (n == 1) return 1;
    return n + calculateSum(n - 1);
  }
}
