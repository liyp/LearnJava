/*
 * Copyright © 2017 liyp (liyp.yunpeng@gmail.com)
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
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatch.CloseMode;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkElector3 {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkElector3.class);

    static String latchPath = "/test/leader";

    public static void main(String[] args) throws Exception {
        MyLeaderElection election = new MyLeaderElection("localhost", "01107");
        election.elect();

        System.out.println("Press enter/return to quit\n");
        new BufferedReader(new InputStreamReader(System.in)).readLine();
        election.close();
    }


    static class MyLeaderElection implements LeaderLatchListener {

        private String id;
        private CuratorFramework client;
        private LeaderLatch latch;

        MyLeaderElection(String host, String id) {
            this.id = id;
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            client = CuratorFrameworkFactory.newClient(host, retryPolicy);
            latch = new LeaderLatch(client, latchPath, id, CloseMode.NOTIFY_LEADER);
            latch.addListener(this);
        }

        public void elect() throws Exception {
            client.start();
            latch.start();
            latch.await();
            LOGGER.info("{} , i am leader.",latch.getLeader().getId());
        }

        public void close() throws IOException {
            latch.close();
            client.close();
        }

        @Override
        public void notLeader() {
            System.out.println("listener: not leader. _id="+id);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void isLeader() {
            System.out.println("listener: be leader. _id="+id);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
