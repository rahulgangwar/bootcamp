package string;

public class CheckPalindrome {
  public static void main(String[] args) {
    String s = "";
    System.out.println(checkPalindrome(s, 0, s.length() - 1));
  }

  private static boolean checkPalindrome(String s, int l, int r) {
    if (s.length() == 0) return true;
    if (l >= r) return true;
    if (s.charAt(l) != s.charAt(r)) return false;

    return checkPalindrome(s, ++l, --r);
  }
}
