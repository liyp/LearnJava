package com.github.liyp.zk.demo;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZKManager elects the leader to process OA publishing tasks handler.
 * 
 * <p>
 * Election Algorithms description:
 * </p>
 * 
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
    private static final Logger logger = LoggerFactory
            .getLogger(ZkElector.class);

    /** the root path of push server nodes */
    private static final String ROOT_PATH = "/ROOT";

    /** the parent path of push server nodes */
    private static final String ELECTION_PATH = ROOT_PATH + "/ELECTION";

    /** the path of the current push server node */
    private String currentPath = null;

    private ZooKeeper zookeeper;

    public ZkElector(String zkAddress, int zkTimeout) throws IOException {
        this.zookeeper = new ZooKeeper(zkAddress, zkTimeout, this);
    }

    @Override
    public void process(WatchedEvent event) {
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
                try {
                    leaderElection();
                } catch (KeeperException | InterruptedException e) {
                    logger.error("ZK path rebuild error.");
                }
                break;
            default:
                logger.info("ZK path {}, state {}", event.getPath(),
                        event.getState());
                break;
            }
            break;
        }
    }

    public void leaderElection() throws KeeperException, InterruptedException {

        // If is the first client, then it should create the znode "/election"
        Stat stat = zookeeper.exists(ELECTION_PATH, false);
        if (stat == null) {
            Stat rootStat = zookeeper.exists(ROOT_PATH, false);
            if (rootStat == null) {
                logger.info("Initialize root path {}.", ROOT_PATH);
                String r = zookeeper.create(ROOT_PATH, new byte[0],
                        Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.info("Zookeeper path {} created.", r);
            }
            logger.info("In the first client, creating {}.", ELECTION_PATH);
            String r = zookeeper.create(ELECTION_PATH, new byte[0],
                    Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("Zookeeper path {} created.", r);
        }

        // Create znode z with path "ELECTION/n_" with both SEQUENCE and
        // EPHEMERAL flags
        String childPath = ELECTION_PATH + "/n_";

        childPath = zookeeper.create(childPath, new byte[0],
                Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        logger.info("My leader proposal created. Path = {}.", childPath);

        currentPath = childPath;

        newLeaderElection();
    }

    public void newLeaderElection()
            throws KeeperException, InterruptedException {

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
        } else {
            logger.info("Current node {} is the follower.", currentPath);
        }
    }

    public static void main(String[] args)
            throws IOException, KeeperException, InterruptedException {
        ZkElector zkElector = new ZkElector("127.0.0.1", 100);
        zkElector.leaderElection();
        System.in.read();
    }

}
