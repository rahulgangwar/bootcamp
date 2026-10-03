package intervals;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinArrowsToBurstBalloons {

    // Approach 1: Using heap
    // Though not optimized since the only use of heap here is to track the min end
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(
                points,
                (a, b) -> {
                    return Integer.compare(a[0], b[0]);
                });

        Queue<int[]> heap =
                new PriorityQueue<>(
                        (a, b) -> {
                            return Integer.compare(a[1], b[1]);
                        });

        int count = 0;
        for (int[] interval : points) {
            int start = interval[0];
            if (heap.isEmpty()) {
                count++;
            } else if (start > heap.peek()[1]) {
                // first interval in heap is not overalapping so empty the heap
                while (!heap.isEmpty()) {
                    heap.poll();
                }
                count++;
            }
            heap.offer(interval);
        }
        return count;
    }

    // Approach 2: Greedy
    // Always point the arrow at the end of the first interval
    // Check if the following interval are overlapping with the arrow position
    // If not move the arrow position to the end of current interval and increase count
    public int findMinArrowShots2(int[][] points) {

        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));

        int arrows = 1;
        int arrowPosition = points[0][1];

        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > arrowPosition) {
                arrows++;
                arrowPosition = points[i][1];
            }
        }

        return arrows;
    }
}
