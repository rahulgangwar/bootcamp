package backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// https://leetcode.com/problems/combinations/description/?envType=study-plan-v2&envId=top-interview-150
public class Combinations {
    public List<List<Integer>> combine(int n, int k) {
        Set<List<Integer>> result = new HashSet<>();
        backtrack(result, new ArrayList<>(), 1, n, k);

        return new ArrayList<>(result);
    }

    public void backtrack(Set<List<Integer>> result, List<Integer> curr, int start, int n, int k) {
        if (curr.size() == k) {
            result.add(curr);
            return;
        }

        for (int i = start; i <= n; i++) {
            curr.add(i);
            backtrack(result, new ArrayList<>(curr), i + 1, n, k);
            curr.remove(Integer.valueOf(i));
        }
    }
}
