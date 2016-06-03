/*
 * Copyright © 2016 liyp (liyp.yunpeng@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liyp.zk.demo;

import java.io.BufferedReader;
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

        LeaderLatch latch = new LeaderLatch(client, latchPath, "112",
                CloseMode.NOTIFY_LEADER);
        latch.addListener(new LeaderLatchListener() {
            @Override
            public void notLeader() {
                System.out.println("listener: not leader.");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void isLeader() {
                System.out.println("listener: be leader.");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
