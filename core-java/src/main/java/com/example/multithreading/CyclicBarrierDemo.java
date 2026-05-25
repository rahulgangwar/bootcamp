package com.example.multithreading;

import java.util.concurrent.CyclicBarrier;

/** MultiplayerGame */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        int numberOfPlayers = 3;
        CyclicBarrier barrier =
                new CyclicBarrier(
                        numberOfPlayers,
                        () -> {
                            System.out.println(
                                    "All players are ready! Starting the next round...\n");
                        });

        for (int i = 1; i <= numberOfPlayers; i++) {
            new Thread(new Player(barrier, i)).start();
        }
    }

    static class Player implements Runnable {
        private final CyclicBarrier barrier;
        private final int playerId;

        public Player(CyclicBarrier barrier, int playerId) {
            this.barrier = barrier;
            this.playerId = playerId;
        }

        @Override
        public void run() {
            try {
                for (int round = 1; round <= 3; round++) {
                    System.out.println("Player " + playerId + " is performing action in round " + round);
                    Thread.sleep((long) (Math.random() * 3000)); // simulate work

                    System.out.println("Player " + playerId + " completed round " + round);
                    barrier.await(); // wait for others to complete
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
