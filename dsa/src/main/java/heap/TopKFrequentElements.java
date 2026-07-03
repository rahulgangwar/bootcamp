package heap;

import java.util.*;

public class TopKFrequentElements {

    public int[] topKFrequent(int[] nums, int k) {


        Map<Integer, Integer> data = new HashMap<>();
        for (int num : nums) {
            data.put(num, data.getOrDefault(num, 0) + 1);
        }

        //Max heap
        Queue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>(
                (a, b) -> {
                    return Integer.compare(b.getValue(), a.getValue());
                }
        );
        heap.addAll(data.entrySet());

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = heap.poll().getKey();
        }
        return result;
    }

}
