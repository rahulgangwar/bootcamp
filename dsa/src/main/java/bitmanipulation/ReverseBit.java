package bitmanipulation;

public class ReverseBit {

    public static void main(String[] args) {
        new ReverseBit().reverseBitsOptimized(4);

    }

    public int reverseBitsOptimized(int n) {
        int i = 0;
        int result = 0;
        while (i < 32) {
            int bit = (n >> i) & 1;
            result = bit << (31 - i) | result;
            i++;
        }

        return result;
    }

    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int i = 0;
        int result = 0;
        while (i < 32) {
            if (getBit(n, i) == 1) {
                result = setBit(result, 31 - i);
            }
            i++;
        }

        return result;
    }

    public int getBit(int num, int position) {
        int bitmask = 1 << position;
        return (bitmask & num) == 0 ? 0 : 1;
    }

    public int setBit(int num, int position) {
        int bitmask = 1 << position;
        return bitmask | num;
    }


}
