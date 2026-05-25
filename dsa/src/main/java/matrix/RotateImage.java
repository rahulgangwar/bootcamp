package matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.com/problems/rotate-image/?envType=study-plan-v2&envId=top-interview-150">...</a>
 */
public class RotateImage {
    public void rotate(int[][] matrix) {
        List<Integer> data = new ArrayList<>();
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = 0; j < matrix.length; j++) {
                data.add(matrix[i][j]);
            }
        }

        int index = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] = data.get(index++);
            }
        }
    }

    public void rotateOptimized(int[][] matrix) {
        int l = 0;
        int r = matrix.length - 1;
        int t = 0;
        int b = matrix.length - 1;
        int length = matrix.length;

        while (l < r && t < b) {
            for (int i = 0; i <= length - 2; i++) {
                // top to right
                int temp = matrix[t + i][r];
                matrix[t + i][r] = matrix[t][l + i];

                // right to bottom
                int temp2 = matrix[b][r - i];
                matrix[b][r - i] = temp;

                // bottom to left
                temp = matrix[b - i][l];
                matrix[b - i][l] = temp2;

                // left to top
                matrix[t][l + i] = temp;
            }
            l++;
            r--;
            t++;
            b--;
            length = length - 2;
        }
    }
}
