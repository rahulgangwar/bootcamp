package _01_sorting;

public class BubbleSort {
    public static void main(String[] args) {
        int[] data = new int[]{5, 1, 4, 2, 8};
        bubbleSort(data);
        for (int i : data) {
            System.out.print(i + ", ");
        }
    }

    //Implement bubble sort
    private static void bubbleSort(int[] data) {
        int n = data.length;
        boolean swapped= false;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    swapped = true;
                }
            }
            if(!swapped){
                //data is already sorted
                break;
            }
        }
    }
}
