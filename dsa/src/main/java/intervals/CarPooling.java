package intervals;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

// https://leetcode.com/problems/car-pooling/
public class CarPooling {

    // Approach 1: Using heap
    public boolean carPooling(int[][] trips, int capacity) {

        // Process trips in order of pickup location
        Arrays.sort(trips, (a, b) -> Integer.compare(a[1], b[1]));

        // [passengers, dropOffLocation]
        // Earliest drop-off comes first
        Queue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

        int total = 0;

        for (int[] trip : trips) {

            int passengers = trip[0];
            int start = trip[1];
            int end = trip[2];

            // Remove passengers whose trip has ended
            while (!heap.isEmpty() && heap.peek()[1] <= start) {
                total -= heap.poll()[0];
            }

            // Add new passengers
            total += passengers;

            // Track when these passengers will leave
            heap.offer(new int[] {passengers, end});

            // Capacity exceeded
            if (total > capacity) {
                return false;
            }
        }

        return true;
    }

    // Approach 2: Using running sum
    public boolean carPooling2(int[][] trips, int capacity) {
        int[] passengers = new int[1001];
        for (int[] trip : trips) {
            passengers[trip[1]] += trip[0];
            passengers[trip[2]] -= trip[0];
        }

        int total = 0;
        for (int count : passengers) {
            total += count;
            if (total > capacity) return false;
        }
        return true;
    }
}
