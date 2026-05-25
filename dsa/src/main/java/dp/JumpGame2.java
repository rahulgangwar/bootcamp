package dp;

/**
 * <a href="https://leetcode.com/problems/jump-game-ii/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * Solution giving TLE - need optimization with dp
 */
public class JumpGame2 {
    public int jump(int[] nums) {
        int count = 0;
        int currCount = 0;
        for (int i = 0; i <= nums[0]; i++) {
            currCount = tryJump(nums, 0, i, 0);
            if (currCount > 0)
                count = count != 0 ? Math.min(count, currCount) : currCount;
        }
        return count;
    }

    public int tryJump(int[] nums, int currIndex, int jump, int totalJumps) {
        totalJumps++;
        if (jump == 0) return -1;
        if (currIndex + jump == nums.length - 1) return totalJumps;
        if (currIndex + jump > nums.length - 1) return -1;

        int count = 0;
        int currCount = 0;
        for (int i = 0; i <= nums[currIndex + jump]; i++) {
            currCount = tryJump(nums, currIndex + jump, i, totalJumps);
            if (currCount > 0)
                count = count != 0 ? Math.min(count, currCount) : currCount;
        }
        return count;
    }

}
