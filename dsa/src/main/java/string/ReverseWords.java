package string;

public class ReverseWords {
  public static void main(String[] args) {
    System.out.println(reverseWords("rahul is a good boy"));
  }

  private static String reverseWords(String s) {
    String result = "";

    int ptr1 = 0;
    int ptr2 = 1;

    while (ptr2 < s.length()) {
      if ((s.charAt(ptr2) == ' ' && s.charAt(ptr2 - 1) != ' ')) {
        int temp = ptr2 - 1;
        while (temp >= ptr1) {
          result = result.concat(String.valueOf(s.charAt(temp)));
          temp--;
        }
        result = result.concat(" ");
      } else if (s.charAt(ptr2) != ' ' && s.charAt(ptr2 - 1) == ' ') {
        ptr1 = ptr2;
      } else if (s.charAt(ptr2) == ' ') {
        result = result.concat(" ");
      }
      ptr2++;
    }
    int temp = s.length() - 1;
    while (temp >= ptr1) {
      result = result.concat(String.valueOf(s.charAt(temp)));
      temp--;
    }
    return result;
  }
}
