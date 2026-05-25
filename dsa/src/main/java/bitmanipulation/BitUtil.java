package bitmanipulation;

public class BitUtil {
    public static String toBinary(int num) {
        return Integer.toBinaryString(num);
    }

    public static int leftShift(int num, int position) {
        return num << position;
    }

    public static int rightShift(int num, int position) {
        return num << position;
    }

    /**
     * Position : [3, 2, 1, 0]
     * If AND with bitmask is 0 then bit is 0 else its 1
     */
    public static int getBit(int num, int position) {
        int bitmask = 1 << position;
        return ((bitmask & num) == 0) ? 0 : 1;
    }

    /**
     * Updates the bit at position to 1
     * OR with bitmask
     */
    public static int setBitToOne(int num, int position) {
        int bitmask = 1 << position;
        return bitmask | num;
    }

    /**
     * Updates the bit at position to 0
     * AND with ~bitmask
     */
    public static int setBitToZero(int num, int position) {
        int bitmask = 1 << position;
        return ~bitmask & num;
    }

    public static int updateBit(int num, int position, int bit) {
        return (bit == 0) ? setBitToZero(num, position) : setBitToOne(num, position);
    }
}
