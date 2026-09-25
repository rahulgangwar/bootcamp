package com.example;

import java.util.HashMap;
import java.util.Map;

public class Solution {

    public int[] twoSum(int[] nums, int target) {
        // Value, Index
        Map<Integer,Integer> data = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            int curr = nums[i];
            if(data.containsKey(target - curr)){
                return new int[]{i, data.get(target-curr)};
            }else{
                data.put(curr, i);
            }
        }
        return new int[]{0,0};
    }
}
