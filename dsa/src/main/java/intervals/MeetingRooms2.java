package intervals;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

// https://www.geeksforgeeks.org/problems/attend-all-meetings-ii/1
public class MeetingRooms2 {
    public int minMeetingRooms(int[] start, int[] end) {
        int[][] intervals = new int[start.length][];
        for (int i = 0; i < start.length; i++) {
            intervals[i] = new int[] {start[i], end[i]};
        }
        Arrays.sort(
                intervals,
                (a, b) -> {
                    return Integer.compare(a[0], b[0]);
                });
        Queue<Integer> heap = new PriorityQueue<>();
        int count = 0;

        for (int[] interval : intervals) {
            int startTime = interval[0];
            int endTime = interval[1];

            if (!heap.isEmpty() && startTime >= heap.peek()) {
                // previous meeting have ended
                heap.poll();
            }

            heap.offer(endTime);
            count = Math.max(count, heap.size());
        }
        return count;
    }
}
