package com.example;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class LeaderBoard {

    private static final String LEADERBOARD_KEY = "leaderboard:game";

    public static void main(String[] args) {

        try (Jedis jedis = new Jedis("localhost", 6379)) {

            // Add Player
            // ZADD leaderboard:game 1500 Rahul
            jedis.zadd(LEADERBOARD_KEY, 1500, "Rahul");
            jedis.zadd(LEADERBOARD_KEY, 1800, "Amit");
            jedis.zadd(LEADERBOARD_KEY, 1200, "Ravi");
            jedis.zadd(LEADERBOARD_KEY, 2000, "Neha");

            // GET TOP 3 PLAYERS
            // ZREVRANGE leaderboard:game 0 2 WITHSCORES
            List<String> topPlayers = jedis.zrevrange(LEADERBOARD_KEY, 0, 2);
            System.out.println("Top 3:");
            for (String player : topPlayers) {
                Double score = jedis.zscore(LEADERBOARD_KEY, player);
                System.out.println(player + " - " + score);
            }

            // GET RANK OF RAHUL
            // ZREVRANK leaderboard:game Rahul
            Long rank = jedis.zrevrank(LEADERBOARD_KEY, "Rahul");
            System.out.println("Rahul's rank = " + (rank + 1));

            // GET RAHUL'S SCORE
            // ZSCORE leaderboard:game Rahul
            Double score = jedis.zscore(LEADERBOARD_KEY, "Rahul");
            System.out.println("Rahul score = " + score);
        }
    }
}
