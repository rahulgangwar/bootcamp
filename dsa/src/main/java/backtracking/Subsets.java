package backtracking;

import java.util.ArrayList;
import java.util.List;

public class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        backtrack(result, curr, 0, nums);
        return result;

    }

    private void backtrack(List<List<Integer>> result,
                           List<Integer> curr,
                           int start,
                           int[] nums) {

        if (start == nums.length) {
            result.add(new ArrayList<>(curr));
            return;
        }

        backtrack(result, curr, start + 1, nums);
        curr.add(nums[start]);
        backtrack(result, curr, start + 1, nums);
        curr.remove(curr.size() - 1);
    }
}
