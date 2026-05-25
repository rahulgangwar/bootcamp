package com.example.sorting;

public class BubbleSort {
  public static void main(String[] args) {
    int arr[] = {2, 3, 4, 1};
    int n = arr.length;
    int temp;
    System.out.println("List size: " + n);
    System.out.println("Initial array ");
    System.out.println("-------------------------------");
    for (int k = 0; k < n; k++) {
      System.out.print(arr[k] + ",");
    }
    System.out.println();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
      System.out.println("After iteration " + i + " >> ");
      System.out.println("-------------------------------");
      for (int k = 0; k < n; k++) {
        System.out.print(arr[k] + ",");
      }
      System.out.println();
    }
  }
}
