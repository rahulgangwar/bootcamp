package arrays;

/**
 * Write a code that can return sum of a range in constant time
 */
public class PrefixSum {
    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6};
        int[] psum = new int[data.length];
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
            psum[i] = sum;
        }
        System.out.println(sumOrRange(psum, 0, 3));

    }

    private static int sumOrRange(int[] psum, int start, int end) {
        return psum[end] - (start > 0 ? psum[start - 1] : 0);
    }
}
