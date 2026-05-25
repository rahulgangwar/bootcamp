package com.example.dsa;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
*
BFS:

Time complexity is O(|V|) where |V| is the number of nodes,you need to traverse all nodes.
Space complecity is O(|V|) as well - since at worst case you need to hold all vertices in the queue.

DFS:

Time complexity is again O(|V|), you need to traverse all nodes.
Space complexity - depends on the implementation, a recursive implementation can have a O(h) space complexity [worst case],
where h is the maximal depth of your tree.
Using an iterative solution with a stack is actually the same as BFS, just using a stack instead of a queue -
so you get both O(|V|) time and space complexity.**/
public class BinaryTree {
  Node root;

  BinaryTree(int key) {
    root = new Node(key);
  }

  BinaryTree() {
    root = null;
  }

  // preorder: root, left, right
  // inorder: left, root, right
  // postorder:left, right, root
  public static void preOrder(Node root) {
    System.out.print(root.data + " ");
    if (root.left != null) {
      preOrder(root.left);
    }
    if (root.right != null) {
      preOrder(root.right);
    }
  }

  public static void dfsIterative(Node root) {
    Stack<Node> stack = new Stack<Node>();
    stack.push(root);

    while (!stack.isEmpty()) {
      Node currentNode = stack.pop();
      System.out.println(currentNode.data + " ");

      if (currentNode.right != null) stack.push(currentNode.right);
      if (currentNode.left != null) stack.push(currentNode.left);
    }
  }

  // breadth first traversal
  public static void breadthFirstSearch(Node root) {
    Queue<Node> queue = new LinkedList<Node>();
    queue.add(root);
    while (!queue.isEmpty()) {
      Node tempNode = queue.poll();
      System.out.print(tempNode.data + " ");

      /* Enqueue left child */
      if (tempNode.left != null) {
        queue.add(tempNode.left);
      }

      /* Enqueue right child */
      if (tempNode.right != null) {
        queue.add(tempNode.right);
      }
    }
  }

  public static void main(String[] args) {
    BinaryTree tree = new BinaryTree();

    /*create root*/
    tree.root = new Node(1);

    /* following is the tree after above statement

        1
      /   \
    null  null     */

    tree.root.left = new Node(2);
    tree.root.right = new Node(3);

    /* 2 and 3 become left and right children of 1
           1
         /   \
        2      3
      /    \    /  \
    null null null null  */

    tree.root.left.left = new Node(4);

    /* 4 becomes left child of 2
               1
           /       \
          2          3
        /   \       /  \
       4    null  null  null
      /   \
     null null
    */

    dfsIterative(tree.root);
  }

  public int heightOfBinaryTree(Node node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + Math.max(heightOfBinaryTree(node.left), heightOfBinaryTree(node.right));
    }
  }

  boolean areIdentical(Node root1, Node root2) {

    /* base cases */
    if (root1 == null && root2 == null) return true;

    if (root1 == null || root2 == null) return false;

    /* Check if the data of both roots is same and data of left and right
    subtrees are also same */
    return (root1.data == root2.data
        && areIdentical(root1.left, root2.left)
        && areIdentical(root1.right, root2.right));
  }

  /* This function returns true if S is a subtree of T, otherwise false */
  boolean isSubtree(Node T, Node S) {
    /* base cases */
    if (S == null) return true;

    if (T == null) return false;

    /* Check the tree with root as current node */
    if (areIdentical(T, S)) return true;

    /* If the tree with root as current node doesn't match then
    try left and right subtrees one by one */
    return isSubtree(T.left, S) || isSubtree(T.right, S);
  }

  static class Node {
    Node left;
    Node right;
    int data;

    Node(int data) {
      this.data = data;
      left = null;
      right = null;
    }
  }
}
