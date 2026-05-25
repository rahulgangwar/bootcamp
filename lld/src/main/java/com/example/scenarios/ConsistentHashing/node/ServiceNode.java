package com.example.scenarios.ConsistentHashing.node;

import com.example.scenarios.ConsistentHashing.ConsistentHashRouter;

import java.util.Arrays;

/** a sample usage for routing a request to services based on requester ip */
public class ServiceNode implements Node {
    private final String ip;

    public ServiceNode(String ip) {
        this.ip = ip;
    }

    @Override
    public String getKey() {
        return ip;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
