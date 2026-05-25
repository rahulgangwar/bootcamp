package arrays;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class HIndex {
    public static void main(String[] args) {
        new HIndex().hIndexOptimized(new int[]{3, 0, 6, 1, 5});
    }

    public int hIndex(int[] citations) {
        int max = Integer.MIN_VALUE;
        for (int j : citations) {
            max = Math.max(max, j);
        }

        int count = 0;
        int hindex = 0;
        for (int i = 1; i <= max; i++) {
            count = 0;
            for (int citation : citations) {
                if (i <= citation) {
                    count++;
                }
                if (i == count) {
                    hindex = Math.max(hindex, i);
                    break;
                }
            }
        }
        return hindex;

    }

    public int hIndexOptimized(int[] citations) {
        int hindex = 0;
        List<Integer> data = IntStream.of(citations).boxed().sorted(Collections.reverseOrder()).toList();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) >= i + 1) {
                hindex = i + 1;
            } else {
                break;
            }
        }
        return hindex;
    }
}
