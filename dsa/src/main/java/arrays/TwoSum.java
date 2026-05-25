package arrays;

import java.util.Arrays;

public class TwoSum {
    public static void main(String[] args) {
        //Notes: Arrays.toString to convert arr to string
        System.out.println(Arrays.toString(new TwoSum().twoSum(new int[]{1, 2, 3}, 3)));
    }

    public int[] twoSum(int[] nums, int target) {
        int result[] = new int[2];

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result;

    }
}
