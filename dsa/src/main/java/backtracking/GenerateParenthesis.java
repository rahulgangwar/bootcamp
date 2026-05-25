package backtracking;

// https://leetcode.com/problems/generate-parentheses

import java.util.*;

public class GenerateParenthesis {
    public List<String> generateParenthesis(int n) {
        Set<String> result = new HashSet<>();
        backtrack(result, "", n);
        return new ArrayList<>(result);
    }

    public void backtrack(Set<String> result, String input, int n) {
        if (input.length() == n * 2) {
            if (isValid(input)) {
                result.add(input);
            }
            return;
        }
        backtrack(result, input + "(", n);
        backtrack(result, input + ")", n);
    }

    public boolean isValid(String input) {
        Stack<String> s = new Stack<>();
        for (char c : input.toCharArray()) {
            if (c == ')') {
                if (s.empty()) return false;
                if (s.pop().equals(")")) return false;
            } else {
                s.push(String.valueOf(c));
            }
        }
        return s.empty();
    }

    public void backtrackOptimized(Set<String> result, String input, int n, int open, int closed) {
        if (input.length() == n * 2) {
            result.add(input);
            return;
        }
        if (open < n) {
            backtrackOptimized(result, input + "(", n, open + 1, closed);
        }
        if (closed < open) {
            backtrackOptimized(result, input + ")", n, open, closed + 1);
        }
    }
}
