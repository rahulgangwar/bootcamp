package recursion;

public class SumOfDigits {
  public static void main(String[] args) {
    System.out.println(calculateSum(1234));
  }

  private static int calculateSum(int i) {
    if (i / 10 == 0) return i;
    return i % 10 + calculateSum(i / 10);
  }
}
