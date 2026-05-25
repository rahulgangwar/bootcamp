package com.example.scenarios.SplitWise.model;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class User {
    private final String id;
    private final String name;
    private final String email;
    private final Map<String, Double> balances;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balances = new ConcurrentHashMap<>();
    }
}
