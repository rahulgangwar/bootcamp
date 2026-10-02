package intervals;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/insert-interval/
public class InsertIntervals {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0, n = intervals.length;
        while (i < n && intervals[i][1] < newInterval[0]) {
            // System.out.println("added-1: " + Arrays.toString(intervals[i]));
            result.add(intervals[i]);
            i++;
        }
        while (i < n && intervals[i][0] <= newInterval[1]) {
            // System.out.println("comparing-: " +Arrays.toString(intervals[i])+ " === "+
            // Arrays.toString(newInterval) + " - "+ i);
            newInterval[0] = Math.min(intervals[i][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[i][1], newInterval[1]);
            i++;
            // System.out.println("merged-: " + Arrays.toString(newInterval) + " - "+ i);
        }
        // System.out.println("added-2: " + Arrays.toString(newInterval));
        result.add(newInterval);
        while (i < n) {
            // System.out.println("added-3: " + Arrays.toString(intervals[i]));
            result.add(intervals[i++]);
        }
        return result.toArray(new int[result.size()][]);
    }
}
