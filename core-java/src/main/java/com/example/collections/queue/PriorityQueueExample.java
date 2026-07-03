package com.example.collections.queue;

import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        //Min heap
        Queue<Integer> minHeap = new PriorityQueue<>();
        minHeap.add(3);
        minHeap.add(4);
        minHeap.add(1);

        while (!minHeap.isEmpty()){
            System.out.println(minHeap.poll());
        }
        Queue<Integer> maxHeap = new PriorityQueue<>((a,b) -> {return Integer.compare(b,a);});

    }
}
