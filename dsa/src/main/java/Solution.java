import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class Solution {

    class LRUCache {
        Map<Integer, Node> cache = new HashMap<>();
        Node head; // most recently used
        Node tail; // least recently used
        int capacity;

        class Node {
            int data;
            Node left;
            Node right;

            public Node(int data) {
                this.data = data;
            }

            public void setData(int data) {
                this.data = data;
            }
        }


        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            if (cache.containsKey(key)) return -1;

            Node value = cache.get(key);

            // remove from the list
            Node prev = value.left;
            Node next = value.right;
            prev.right = next;
            next.left = prev;

            // insert at head as it is now the MRU
            next = head.right;
            value.right = next;
            value.left = null;
            head = value;
            return value.data;
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                cache.get(key).setData(value);
            } else {
                Node curr = new Node(value);
                if (cache.size() == capacity) {
                    //remove tail (LRU)
                    Node prev = tail.left;
                    tail = prev;
                    tail.right = null;

                    //insert at head (MRU)
                    Node next = head.right;
                    curr.right = next;
                    head = curr;
                }
                cache.put(key, curr);
            }
        }
    }

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
}
