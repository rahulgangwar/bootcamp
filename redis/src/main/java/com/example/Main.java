package com.example;

import redis.clients.jedis.Jedis;

public class Main {
    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            // Check connection
            System.out.println(jedis.ping());
            // SET
            jedis.set("name", "Rahul");
            // GET
            String name = jedis.get("name");
            System.out.println("Name = " + name);
        }
    }
}
