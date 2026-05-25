package string;

public class CheckSubstring {
  public static void main(String[] args) {
    System.out.println(isSubstring("rahul", "ahl"));
  }

  private static boolean isSubstring(String data, String substring) {
    int index = 0;

    boolean isSubstring = false;
    while (index <= data.length() - substring.length()) {
      for (int i = 0; i < substring.length(); i++) {
        if (data.charAt(i + index) == substring.charAt(i)) {
          if (i == substring.length() - 1) {
            isSubstring = true;
          }
        } else {
          index++;
          break;
        }
      }
      if (isSubstring) break;
    }
    return isSubstring;
  }
}
