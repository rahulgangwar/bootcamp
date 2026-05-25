package com.example.dsa;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Time complexity of both BFS and DFS will be O(V + E), where V is the number of vertices, and
 * E is the number of Edges. This again depends on the data strucure that we user to represent the
 * graph. If it is an adjacency matrix, it will be O(V^2) . If we use an adjacency list, it will be
 * O(V+E).
 */
@SuppressWarnings("unchecked")
public class Graph {

  @SuppressWarnings("unused")
  private int V; // No. of vertices

  private LinkedList<Integer>[] adj; // Adjacency Lists

  // constructor
  Graph(int V) {
    this.V = V;

    // define the size of array as
    // number of vertices
    adj = new LinkedList[V];

    // Create a new list for each vertex
    // such that adjacent nodes can be stored
    for (int i = 0; i < V; i++) {
      adj[i] = new LinkedList<Integer>();
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(4);

    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(2, 0);
    g.addEdge(2, 3);
    g.addEdge(3, 3);

    System.out.println("Following is Breadth First Traversal " + "(starting from vertex 2)");

    g.bfs(0);
  }

  // Function to add an edge into the graph
  void addEdge(int v, int w) {
    adj[v].add(w);
  }

  private void bfs(int node) {
    Queue<Integer> toVisit = new LinkedList<Integer>();
    toVisit.add(node);

    boolean[] visited = new boolean[V];

    int currentNode;
    while (!toVisit.isEmpty()) {
      currentNode = toVisit.poll();
      System.out.println(currentNode + " ");

      visited[currentNode] = true;

      LinkedList<Integer> adjacentNode = adj[currentNode];
      Iterator<Integer> iterator = adjacentNode.iterator();

      int tempNode;
      while (iterator.hasNext()) {
        tempNode = iterator.next();
        if (!visited[tempNode]) {
          visited[tempNode] = true;
          toVisit.add(tempNode);
        }
      }
    }
  }
}
