package arrays.two_pointer;

/**
 * <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 */
public class RemoveDuplicates2 {
    public int removeDuplicates(int[] nums) {
        int i = 1;
        int j = 1;
        int currCount = 1;

        while (j < nums.length) {
            if (nums[i - 1] != nums[j] || currCount < 2) {
                if (nums[i - 1] != nums[j]) {
                    currCount = 1;
                } else {
                    currCount++;
                }
                nums[i] = nums[j];
                i++;
            }
            j++;
        }
        return i;

    }

    public int removeDuplicatesOptimized(int[] nums) {
        int i = 2;
        int j = 2;

        while (j < nums.length) {
            if (nums[i - 2] != nums[j]) {
                nums[i++] = nums[j];
            }
            j++;
        }
        return i;
    }
}
