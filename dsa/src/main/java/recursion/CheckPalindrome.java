package recursion;

public class CheckPalindrome {
  public static void main(String[] args) {
    String s = "aabbaa";
    System.out.println(checkPalindrome(0, s.length() - 1, s));
  }

  private static boolean checkPalindrome(int first, int last, String s) {
    if (first >= last) return true;
    if (s.charAt(first) != s.charAt(last)) return false;
    else return checkPalindrome(++first, --last, s);
  }
}
