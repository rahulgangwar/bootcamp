package com.example.dsa;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1) Create a new tree 2) DFS traversal 3) BFS traversal 4) Find height of tree 5) Find if 2 trees
 * are identical 6) Find if S is subtree of T
 */
public class MyBinaryTree {
  Node root = null;

  /** Creates a new tree and return the root node */
  private static Node createTree() {
    Node root = new Node(0);

    // First level
    root.left = new Node(1);
    root.right = new Node(2);

    // Second level
    root.left.left = new Node(3);
    root.left.right = new Node(4);

    root.right.left = new Node(5);
    root.right.right = new Node(6);
    root.right.right.right = new Node(7);
    return root;
  }

  private static Node createTree2() {
    Node root = new Node(0);

    // First level
    root.left = new Node(1);
    root.right = new Node(2);

    // Second level
    root.left.left = new Node(3);
    root.left.right = new Node(4);

    root.right.left = new Node(5);
    root.right.right = new Node(6);
    root.right.right.right = new Node(7);
    root.right.right.right.right = new Node(8);
    return root;
  }

  /** DFS traversal */
  private static void dfs(Node root) {
    System.out.println(root.data);
    if (root.left != null) dfs(root.left);
    if (root.right != null) dfs(root.right);
  }

  /** BFS traversal */
  private static void bfs(Node root) {
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      Node current = queue.poll();
      System.out.println(current.data);

      if (current.left != null) queue.add(current.left);
      if (current.right != null) queue.add(current.right);
    }
  }

  private static int findHeight(Node root) {
    if (root == null) return 0;
    else return 1 + Math.max(findHeight(root.left), findHeight(root.right));
  }

  private static boolean areIdentical(Node first, Node second) {
    if (first == null && second == null) return true;
    if (first == null || second == null) return false;
    if (first.data != second.data) return false;

    return areIdentical(first.left, second.left) && areIdentical(first.right, second.right);
  }

  public static void main(String[] args) {
    System.out.println(areIdentical(createTree(), createTree()));
  }

  private static class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
      this.data = data;
    }
  }
}
