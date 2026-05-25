package string;

import java.util.Arrays;

/**
 * <a href="https://leetcode.com/problems/zigzag-conversion/?envType=study-plan-v2&envId=top-interview-150"/>
 */
public class ZigZagConversion {
    public String convert(String s, int numRows) {
        boolean moveDown = false;
        int rowIndex = 0;

        String[] rows = new String[numRows];
        Arrays.fill(rows, "");

        for (int i = 0; i < s.length(); i++) {
            rows[rowIndex] += s.charAt(i);

            if (rowIndex == numRows - 1 || rowIndex == 0) {
                moveDown = !moveDown;
            }

            if (numRows > 1) {
                rowIndex += moveDown ? 1 : -1;
            }
        }

        StringBuilder result = new StringBuilder();
        for (String curr : rows) {
            result.append(curr);
        }

        return result.toString();
    }
}
