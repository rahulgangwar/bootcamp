package arrays;

public class GasStationCircuit {
    public static void main(String[] args) {
        System.out.println(
                new GasStationCircuit().canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2})
        );

    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int length = gas.length;
        int currGas = 0;
        int costOfLastStop = 0;
        for (int i = 0; i < length; i++) {
            currGas = 0;
            for (int j = 0; j < length; j++) {
                int index = (i + j) % (length - 1);
                if (j == 0) {
                    currGas = gas[index];
                } else {
                    currGas = currGas - costOfLastStop + gas[index];
                }
                costOfLastStop = cost[index];
                if (currGas < 0) {
                    break;
                }
                if (j == length - 1) return i;
            }
        }
        return -1;

    }
}
