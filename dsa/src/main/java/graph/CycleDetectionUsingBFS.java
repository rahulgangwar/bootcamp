package graph;

import java.util.*;

public class CycleDetectionUsingBFS {
    public static void main(String[] args) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2));
        graph.put(2, Arrays.asList(1, 5));
//        graph.put(3, Arrays.asList(1, 4, 6));
        graph.put(4, Arrays.asList(3));
        graph.put(5, Arrays.asList(2, 7));
        graph.put(6, Arrays.asList(3, 7));
        graph.put(7, Arrays.asList(5, 6));

        CycleDetectionUsingBFS cycleDetector = new CycleDetectionUsingBFS();
        boolean hasCycle = cycleDetector.detectCycle(graph);
        System.out.println("Cycle detected: " + hasCycle);
    }

    public boolean detectCycle(Map<Integer, List<Integer>> graph) {
        Set<Integer> visited = new HashSet<>();
        for (Integer node : graph.keySet()) {
            if (!visited.contains(node) && helper(graph, node, visited)) {
                return true;
            }
        }
        return false;
    }

    public boolean helper(Map<Integer, List<Integer>> graph, int start, Set<Integer> visited) {
        Queue<int[]> queue = new LinkedList<>();
        // start, current
        queue.offer(new int[] {-1, start});
        visited.add(start);

        while ((!queue.isEmpty())) {
            int[] data = queue.poll();
            int parentNode = data[0];
            int curNode = data[1];

            for (int neighbour : graph.getOrDefault(curNode, new ArrayList<Integer>())) {
                if (neighbour == parentNode) {
                    // Cycle detected
                    return true;
                } else if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.offer(new int[] {curNode, neighbour});
                }
            }
        }
        return false;
    }
}
