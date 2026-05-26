package _01_sorting;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] data = new int[]{5, 3, 2, 0, 1, 4};
        new MergeSort().mergeSort(data, 0, data.length-1);
        System.out.println(Arrays.toString(data));
    }

    private void mergeSort(int[] data, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(data, l, m);
            mergeSort(data, m + 1, r);
            merge(data, l, m, r);
        }
    }

    private void merge(int[] data, int l, int m, int r) {
        //Create temp arrays to store the data
        int arr1Size = m - l + 1;
        int[] arr1 = new int[arr1Size];
        int arr2Size = r - m;
        int[] arr2 = new int[arr2Size];

        //Populate temp arrays
        System.arraycopy(data, l, arr1, 0, arr1Size);
        System.arraycopy(data, m + 1, arr2, 0, arr2Size);

        //Compare and copy
        int start = l;
        int i = 0, j = 0;
        while (i < arr1Size && j < arr2Size) {
            if (arr1[i] < arr2[j]) {
                data[start++] = arr1[i++];
            } else {
                data[start++] = arr2[j++];
            }
        }

        //Copy the remaining elements
        while (i < arr1Size) {
            data[start++] = arr1[i++];
        }
        while (j < arr2Size) {
            data[start++] = arr2[j++];
        }
    }
}
