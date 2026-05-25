package arrays;

public class SecondLargestElement {
    public static void main(String[] args) {
        int[] data = {12, 35, 1, 10, 34, 1};
        System.out.println(new SecondLargestElement().print2largest(data, data.length));
    }

    int print2largest(int arr[], int n) {
        int largest = arr[0];
        int secondLargest = -1;

        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num < largest && num > secondLargest) {
                secondLargest = num;
            }
        }
        return secondLargest;
    }
}
