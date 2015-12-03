package com.github.liyp.mapdb.test;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class QueueTest {

    public static void main(String[] args) {
        DB db = DBMaker.newFileDB(new File("_db")).closeOnJvmShutdown()
                .transactionDisable().make();
        
        BlockingQueue<String> queue = db.createQueue("queue", null, false);
    }

}
