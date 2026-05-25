import java.util.*;

public class Rough {
    public static void main(String[] args) {
        System.out.println(new Rough().countPalindromicSubsequence("adc"));
    }

    public int countPalindromicSubsequence(String s) {
        Set<String> palindromes = new HashSet<>();
        helper(s, 0, new StringBuilder(), palindromes);
        System.out.println(palindromes);
        return palindromes.size();
    }

    private void helper(String s, int index, StringBuilder curr, Set<String> palindromes) {

        if (curr.length() == 3 && curr.toString().equals(curr.reverse().toString())) {
            palindromes.add(curr.toString());
            return;
        }

        for (int i = index; i < s.length(); i++) {
            curr.append(s.charAt(i));
            helper(s, index + 1, curr, palindromes);
            curr.setLength(curr.length() - 1);
            helper(s, index + 1, curr, palindromes);
        }
    }
};

