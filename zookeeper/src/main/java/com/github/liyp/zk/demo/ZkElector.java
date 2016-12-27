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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZKManager elects the leader to process OA publishing tasks handler.
 * <p/>
 * <p>
 * Election Algorithms description:
 * </p>
 * <p/>
 * Let ELECTION be a path of choice of the application. To volunteer to be a
 * leader: 1. Create znode z with path "ELECTION/n_" with both SEQUENCE and
 * EPHEMERAL flags; 2. Let C be the children of "ELECTION", and i be the
 * sequence number of z; 3. Watch for changes on "ELECTION/n_j", where j is the
 * smallest sequence number such that j < i and n_j is a znode in C; Upon
 * receiving a notification of znode deletion: a) Let C be the new set of
 * children of ELECTION; b) If z is the smallest node in C, then execute leader
 * procedure; c) Otherwise, watch for changes on "ELECTION/n_j", where j is the
 * smallest sequence number such that j < i and n_j is a znode in C;
 */
public class ZkElector implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(ZkElector.class);

    /**
     * the root path of push server nodes
     */
    private static final String ROOT_PATH = "/PUSH";

    /**
     * the parent path of push server nodes
     */
    private static final String ELECTION_PATH = ROOT_PATH + "/ELECTION";

    /**
     * the path of the current push server node
     */
    private String currentPath = null;

    private ZooKeeper zookeeper;

    private String zkAddress;
    private int zkTimeout;

    private AtomicBoolean elected = new AtomicBoolean();

    private TaskServer taskServer = new TaskServer();

    public ZkElector(String zkAddress, int zkTimeout) throws IOException {
        this.zookeeper = new ZooKeeper(zkAddress, zkTimeout, this);
        elected.set(true);
        this.zkAddress = zkAddress;
        this.zkTimeout = zkTimeout;
    }

    @Override
    public void process(WatchedEvent event) {
        logger.info("ZK path ,state , type: {}", event.getPath() + " " + event.getState() + " " + event.getType() + "    #"+zookeeper.hashCode());
        switch (event.getType()) {
            case NodeChildrenChanged:
            case NodeCreated:
            case NodeDataChanged:
                break;
            case NodeDeleted:
                try {
                    newLeaderElection();
                } catch (KeeperException | InterruptedException e) {
                    logger.error("New leader election failed.", e);
                }
                break;
            case None:
                switch (event.getState()) {
                    case Disconnected:
                        logger.info("ZK path {} Disconnected.", event.getPath());
                        break;
                    case Expired:
                        logger.info("ZK path {} Expired.", event.getPath());

                            taskServer.shutdown();
                        try {
                            this.zookeeper = new ZooKeeper(zkAddress, zkTimeout, this);
                            elected.set(true);
                        } catch (IOException e) {
                            logger.info("{}",this.hashCode(),e);
                        }
                        break;
                    case SyncConnected:
                        logger.info("ZK path {} SyncConnected.", event.getPath());
                        try {
                            if(elected.get()) {
                                leaderElection();
                                elected.set(false);
                            }
                        } catch (KeeperException | InterruptedException e) {
                            logger.error("ZK path rebuild error.", e);
                        } catch (Exception e) {
                            logger.info("{}",this.hashCode(),e);
                        }
                        break;
                    default:
                        logger.info("ZK path {}, state {}", event.getPath(), event.getState());
                        break;
                }
                break;
        }
    }

    public void leaderElection() throws KeeperException, InterruptedException {

        // If is the first client, then it should create the znode "/election"
        Stat stat = zookeeper.exists(ELECTION_PATH, false);
        if (stat == null) {
            try {
                Stat rootStat = zookeeper.exists(ROOT_PATH, false);
                if (rootStat == null) {
                    logger.info("Initialize root path {}.", ROOT_PATH);
                    String r = zookeeper.create(ROOT_PATH, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    logger.info("Zookeeper path {} created.", r);
                }
                logger.info("In the first client, creating {}.", ELECTION_PATH);
                String r = zookeeper.create(ELECTION_PATH, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.info("Zookeeper path {} created.", r);
            } catch (NodeExistsException nee) {
                logger.warn("Zookeeper path has existed.", nee.getMessage());
            }
        }

        // Create znode z with path "ELECTION/n_" with both SEQUENCE and
        // EPHEMERAL flags
        String childPath = ELECTION_PATH + "/n_";

        childPath = zookeeper.create(childPath, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        logger.info("My leader proposal created. Path = {}.", childPath);

        currentPath = childPath;

        newLeaderElection();
    }

    public void newLeaderElection() throws KeeperException, InterruptedException {

        // Let C be the children of "ELECTION", and i be the sequence number of
        // znode;
        // Watch for changes on "ELECTION/n_j", where j is the smallest sequence
        // number such that j < i and n_j is a znode in C;

        List<String> children = zookeeper.getChildren(ELECTION_PATH, false);

        String smallestNode = children.get(0);

        for (String s : children) {
            if (s.compareTo(smallestNode) < 0)
                smallestNode = s;
        }

        // smallestNode contains the smallest sequence number
        String leader = ELECTION_PATH + "/" + smallestNode;

        // Watch the leader
        zookeeper.exists(leader, true);
        logger.info("Leader is the owner of znode: {}", leader);

        // Only the leader starts the scheduled task.
        if (currentPath.equals(leader)) {
            logger.info("Current node is the leader.");
            taskServer.start();
        } else {
            logger.info("Current node {} is the follower.", currentPath);
        }
    }

    static class TaskServer implements Runnable {
        AtomicBoolean running = new AtomicBoolean();
        Thread serverThread;
        long timestamp = System.currentTimeMillis();

        public void shutdown() {
            logger.info("TaskServer stop...");
            running.compareAndSet(true, false);
        }

        public void start() {
            running.compareAndSet(false, true);
            logger.info("TaskServer start...");
            serverThread = new Thread(this);
            serverThread.start();
        }

        @Override
        public void run() {
            int i = 0;
            while(running.get()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignore) {
                }
                if(++i%5 == 0) {
                    logger.info("TaskServer#{}-{} run ... {}", timestamp, serverThread.hashCode(), i);
                }
            }
        }
    }

    public static void main(String[] args)
            throws IOException, KeeperException, InterruptedException {
        ZkElector zkElector = new ZkElector("127.0.0.1", 6000);
        System.in.read();
    }

}
