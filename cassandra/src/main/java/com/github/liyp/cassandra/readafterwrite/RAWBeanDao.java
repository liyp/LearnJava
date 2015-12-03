package com.github.liyp.cassandra.readafterwrite;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

public class RAWBeanDao {

    public static Lock lock = new ReentrantLock();

    private Cluster cluster;
    private Session session;
    private MappingManager mappingManager;

    public RAWBeanDao(String addresses, String keyspace) {
        cluster = Cluster.builder().addContactPoints(addresses).build();
        session = cluster.connect(keyspace);
        mappingManager = new MappingManager(session);
    }

    public MappingManager getMappingManager() {
        return mappingManager;
    }

}
