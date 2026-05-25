package recursion;

public class SubsetsOfString {
  public static void main(String[] args) {
    subset(0, "rah", "");
  }

  private static void subset(int index, String s, String result) {
    if (index == s.length()) {
      System.out.println(result);
      return;
    }
    subset(index + 1, s, result.concat(String.valueOf(s.charAt(index))));
    subset(index + 1, s, result);
  }
}
