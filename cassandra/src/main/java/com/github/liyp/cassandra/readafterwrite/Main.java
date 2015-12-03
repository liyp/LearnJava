package com.github.liyp.cassandra.readafterwrite;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    static String address = "192.168.137.199";
    static String keyspace = "test";

    public static void main(String[] args) throws IOException {
        RAWBeanDao readDao = new RAWBeanDao(address, keyspace);
        ScheduledExecutorService readExec = Executors
                .newSingleThreadScheduledExecutor();
        readExec.scheduleWithFixedDelay(new ReadOpt(readDao), 100, 100,
                TimeUnit.MILLISECONDS);
        ScheduledExecutorService writeExec = Executors
                .newSingleThreadScheduledExecutor();
        writeExec.scheduleWithFixedDelay(new WriteOpt(readDao), 1, 10,
                TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }));
        while (true) {
        }
    }

}
