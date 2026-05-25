package recursion;

import java.util.*;

public class PossibleWordsFromPhoneDigits {
  // Function to find list of all words possible by pressing given numbers.
  static ArrayList<String> possibleWords(int a[], int N) {
    Map<Integer, List<String>> data = new HashMap<>();
    data.put(2, Arrays.asList("a", "b", "c"));
    data.put(3, Arrays.asList("d", "e", "f"));
    data.put(4, Arrays.asList("g", "h", "i"));
    data.put(5, Arrays.asList("j", "k", "l"));
    data.put(6, Arrays.asList("m", "n", "o"));
    data.put(7, Arrays.asList("p", "q", "r", "s"));
    data.put(8, Arrays.asList("t", "u", "v"));
    data.put(9, Arrays.asList("w", "x", "y", "z"));

    return words(a, N, 0, data, "");
  }

  static ArrayList<String> words(
      int[] a, int N, int i, Map<Integer, List<String>> data, String result) {
    ArrayList<String> list = new ArrayList<>();
    if (i == N) {
      list.add(result);
      return list;
    }

    int num = a[i];
    if (data.containsKey(num)) {
      List<String> characters = data.get(num);
      for (String s : characters) {
        list.addAll(words(a, N, i + 1, data, result + s));
      }
      return list;
    } else {
      list.addAll(words(a, N, i + 1, data, result));
      return list;
    }
  }
}
