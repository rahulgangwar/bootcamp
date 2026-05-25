package string;

public class LongestSubstringWithDistinctCharacters {
  public static void main(String[] args) {
    System.out.println(find("rahaul"));
  }

  private static int find(String s) {
    int[] data;
    int max = 0;
    for (int i = 0; i < s.length(); i++) {
      data = new int[256];
      int count = 0;
      for (int j = i; j < s.length(); j++) {
        if (data[s.charAt(j)] == 0) {
          data[s.charAt(j)]++;
          count++;
        } else {
          break;
        }
      }
      max = Math.max(max, count);
    }
    return max;
  }
}
