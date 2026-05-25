package arrays.sliding_window;

/**
 * <a href="https://leetcode.com/problems/minimum-size-subarray-sum/submissions/1404585133/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * Sliding Window
 */
public class MinSizeSubarraySum {
    public int minSubArrayLen(int target, int[] nums) {
        int sum;
        int subArrayLength = 0;
        int length;
        for (int i = 0; i < nums.length; i++) {
            sum = 0;
            length = 1;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum >= target) {
                    subArrayLength = (subArrayLength == 0) ? length : Math.min(subArrayLength, length);
                    break;
                }
                length++;
            }
        }
        return subArrayLength;
    }

    public int minSubArrayLenOptimized(int target, int[] nums) {
        int sum = 0;
        int subArrayLength = Integer.MAX_VALUE;
        int left=0;
        int right=0;
        while(right < nums.length){
            sum += nums[right];
            while(sum >= target){
                subArrayLength = Math.min(subArrayLength, right-left+1);
                sum -= nums[left];
                left++;
            }
            right++;
        }

        return subArrayLength == Integer.MAX_VALUE ? 0 : subArrayLength;
    }
}
