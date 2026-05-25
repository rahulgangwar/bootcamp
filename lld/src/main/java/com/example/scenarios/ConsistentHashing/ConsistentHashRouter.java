package com.example.scenarios.ConsistentHashing;

import com.example.scenarios.ConsistentHashing.hashing.HashFunction;
import com.example.scenarios.ConsistentHashing.node.Node;
import com.example.scenarios.ConsistentHashing.hashing.MD5Hash;
import com.example.scenarios.ConsistentHashing.node.VirtualNode;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashRouter<T extends Node> {
    private final SortedMap<Long, VirtualNode<T>> ring = new TreeMap<>();
    private final HashFunction hashFunction;

    public ConsistentHashRouter(Collection<T> physicalNodes, int virtualNodeCount, HashFunction hashFunction) {
        if (hashFunction == null) {
            throw new NullPointerException("Hash Function is null");
        }
        this.hashFunction = hashFunction;
        if (physicalNodes != null) {
            for (T physicalNode : physicalNodes) {
                addNode(physicalNode, virtualNodeCount);
            }
        }
    }

    public void addNode(T physicalNode, int virtualNodeCount) {
        if (virtualNodeCount < 0)
            throw new IllegalArgumentException("Illegal virtual node counts :" + virtualNodeCount);
        int existingReplicas = getExistingReplicas(physicalNode);
        for (int i = 0; i < virtualNodeCount; i++) {
            VirtualNode<T> virtualNode = new VirtualNode<>(physicalNode, existingReplicas + i);
            ring.put(hashFunction.hash(virtualNode.getKey()), virtualNode);
        }
    }

    public void removeNode(T physicalNode) {
        Iterator<Long> it = ring.keySet().iterator();
        while (it.hasNext()) {
            Long key = it.next();
            VirtualNode<T> virtualNode = ring.get(key);
            if (virtualNode.isVirtualNodeOf(physicalNode)) {
                it.remove();
            }
        }
    }

    public T getNode(String objectKey) {
        if (ring.isEmpty()) {
            return null;
        }
        Long hashVal = hashFunction.hash(objectKey);
        SortedMap<Long, VirtualNode<T>> tailMap = ring.tailMap(hashVal);
        Long nodeHashVal = !tailMap.isEmpty() ? tailMap.firstKey() : ring.firstKey();
        return ring.get(nodeHashVal).getPhysicalNode();
    }

    public int getExistingReplicas(T physicalNode) {
        int replicas = 0;
        for (VirtualNode<T> virtualNode : ring.values()) {
            if (virtualNode.isVirtualNodeOf(physicalNode)) {
                replicas++;
            }
        }
        return replicas;
    }
}
