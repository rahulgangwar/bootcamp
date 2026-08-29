package com.example.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CuratorLeaderElectionDemo {
    private static final String ZOOKEEPER_SERVER = "localhost:2181,localhost:2182,localhost:2183";
    private static final String ELECTION_PATH = "/curator-election";

    public static void main(String[] args) throws Exception {
        Thread worker1 = new Thread(new Worker("Worker-1"), "Worker-1");
        Thread worker2 = new Thread(new Worker("Worker-2"), "Worker-2");
        Thread worker3 = new Thread(new Worker("Worker-3"), "Worker-3");

        worker1.start();
        Thread.sleep(1000);

        worker2.start();
        Thread.sleep(1000);

        worker3.start();

        worker1.join();
        worker2.join();
        worker3.join();
    }

    static class Worker implements Runnable {
        private final String workerName;

        Worker(String workerName) {
            this.workerName = workerName;
        }

        @Override
        public void run() {
            CuratorFramework client =
                    CuratorFrameworkFactory.builder()
                            .connectString(ZOOKEEPER_SERVER)
                            .sessionTimeoutMs(10_000)
                            .connectionTimeoutMs(5_000)
                            .retryPolicy(new ExponentialBackoffRetry(1_000, 3))
                            .build();

            client.start();

            LeaderSelector leaderSelector = getLeaderSelector(client);
            System.out.println(workerName + " joining leader election");

            leaderSelector.start();
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private @NonNull LeaderSelector getLeaderSelector(CuratorFramework client) {
            LeaderSelector leaderSelector =
                    new LeaderSelector(
                            client,
                            ELECTION_PATH,
                            new LeaderSelectorListenerAdapter() {
                                @Override
                                public void takeLeadership(CuratorFramework client)
                                        throws Exception {
                                    System.out.println(workerName + " >>> I AM THE LEADER <<<");
                                    Thread.sleep(5_000);
                                    System.out.println(workerName + " releasing leadership");
                                }
                            });

            leaderSelector.autoRequeue();
            return leaderSelector;
        }
    }
}
