package com.github.liyp.mapdb;

import java.util.concurrent.atomic.AtomicLong;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

public class ExpiryCache {

    private DB db;
    private HTreeMap<Long, String> map;
    private AtomicLong currPoint;

    private ExpiryCache() {
        db = DBMaker.newMemoryDirectDB().asyncWriteEnable().closeOnJvmShutdown()
                .transactionDisable().make();

    }

}
