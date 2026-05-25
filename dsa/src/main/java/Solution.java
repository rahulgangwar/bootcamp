import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] forbidden = {8,3,16,6,12,20};
        int frwd = 15;
        int bkwd = 13;
        int target = 11;
        System.out.println(s.minimumJumps(forbidden, frwd, bkwd, target));
    }

    class Pair {
        int pos;
        boolean isLastBkwd;

        public Pair(int pos, boolean isLastBkwd) {
            this.pos = pos;
            this.isLastBkwd = isLastBkwd;
        }

        public boolean equals(Pair p) {
            return this.pos == p.pos && this.isLastBkwd == p.isLastBkwd;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "pos=" + pos +
                    ", isLastBkwd=" + isLastBkwd +
                    '}';
        }
    }

    public int minimumJumps(int[] forbidden, int frwd, int bkwd, int target) {
        Queue<Pair> q = new LinkedList<>();
        Set<Pair> seen = new HashSet<>();
        int ans = 0;
        int nextPos = 0;

        Set<Integer> forbiddenSet = new HashSet<>();
        for (int i : forbidden) {
            forbiddenSet.add(i);
        }

        Pair p = new Pair(0, false);
        q.offer(p);
        seen.add(p);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                p = q.poll();
                System.out.println(p.pos);
                if (p.pos == target) return ans;

                // jump forward
                nextPos = p.pos + frwd;
                if (!forbiddenSet.contains(nextPos) && nextPos < 2000 + Math.max(frwd, bkwd) && !seen.contains(new Pair(nextPos, false))) {
                    q.offer(new Pair(nextPos, false));
                    seen.add(new Pair(nextPos, false));
                }

                // jump backward
                nextPos = p.pos - bkwd;
                if (!forbiddenSet.contains(nextPos) && nextPos >= 0 && !seen.contains(new Pair(nextPos, true))) {
                    q.offer(new Pair(nextPos, true));
                    seen.add(new Pair(nextPos, true));
                }
            }
            ans++;
        }
        return -1;
    }
}
