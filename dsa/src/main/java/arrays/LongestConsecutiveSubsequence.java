package arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
https://leetcode.com/problems/longest-consecutive-sequence/?envType=study-plan-v2&envId=top-interview-150
 */
public class LongestConsecutiveSubsequence {
    public int longestConsecutive(int[] nums) {
        Set<Integer> data = new HashSet<>();

        for (int i : nums) {
            data.add(i);
        }

        int length = 0;
        int maxLength = 0;
        for (int i : nums) {
            if (!data.contains(i - 1)) {
                length = 1;
                while (data.contains(i + length)) {
                    length++;
                }
                maxLength = Math.max(maxLength, length);
            }
        }
        return maxLength;
    }

    public int longestConsecutiveOptimized(int[] nums) {
        if (nums.length < 2) return nums.length;

        Arrays.sort(nums);

        int count = 1;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) continue;
            if (nums[i - 1] + 1 == nums[i]) {
                count++;
            } else {
                max = Math.max(max, count);
                count = 1;
            }
        }
        return Math.max(max, count);
    }
}
