package recursion;

public class RopeCuttingProblem {

  public static void main(String[] args) {
    System.out.println(makeCut(9, 2, 2, 2, 0));
  }

  private static int makeCut(int ropeLength, int a, int b, int c, int totalCuts) {
    if (ropeLength == 0) return totalCuts;
    if (ropeLength < 0) return -1;
    totalCuts++;
    return Math.max(
        Math.max(
            makeCut(ropeLength - a, a, b, c, totalCuts),
            makeCut(ropeLength - b, a, b, c, totalCuts)),
        makeCut(ropeLength - c, a, b, c, totalCuts));
  }
}
