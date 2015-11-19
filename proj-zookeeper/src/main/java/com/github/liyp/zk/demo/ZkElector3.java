package com.github.liyp.zk.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatch.CloseMode;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkElector3 {

    static String latchPath = "/test/leader";

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost",
                retryPolicy);

        LeaderLatch latch = new LeaderLatch(client, latchPath, "111",
                CloseMode.NOTIFY_LEADER);
        latch.addListener(new LeaderLatchListener() {
            @Override
            public void notLeader() {
                System.out.println("listener: not leader.");
            }

            @Override
            public void isLeader() {
                System.out.println("listener: be leader.");
            }
        });

        client.start();
        latch.start();

        latch.await();

        System.out.println(latch.getLeader().getId() + ", i am leader.");

        System.out.println("Press enter/return to quit\n");
        new BufferedReader(new InputStreamReader(System.in)).readLine();
        latch.close();
        client.close();
    }
}
