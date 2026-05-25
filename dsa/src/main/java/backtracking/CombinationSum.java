package backtracking;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/combination-sum/description/?envType=study-plan-v2&envId=top-interview-150

public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, result, new ArrayList<>(), 0, 0);
        return new ArrayList<>(result);
    }

    public void backtrack(int[] candidates, int target, List<List<Integer>> result, List<Integer> curr, int index, int sum) {
        if (sum == target) {
            result.add(curr);
            return;
        }
        if (sum > target) return;

        for (int i = index; i < candidates.length; i++) {
            curr.add(candidates[i]);
            backtrack(candidates, target, result, new ArrayList<>(curr), i, sum + candidates[i]);
            curr.remove(Integer.valueOf(candidates[i]));
        }
    }
}
