package com.example.collections.queue;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class FIFOQueues {
    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(2);
        queue.offer(4);
        queue.offer(1);

        while(!queue.isEmpty()){
            System.out.println(queue.poll());
        }
    }
}
