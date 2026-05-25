package arrays;

import java.util.Arrays;

public class RemoveDuplicates {
    public static void main(String[] args) {
        int[] data = {1, 2, 2, 3, 4, 4, 5, 6, 6, 7};
        int i = 1;
        int j = 1;

        while (j < data.length) {
            if(data[j] != data[j-1]) {
                data[i] = data[j];
                i++;
            }
            j++;
        }
        System.out.println(Arrays.toString(data));
    }
}
