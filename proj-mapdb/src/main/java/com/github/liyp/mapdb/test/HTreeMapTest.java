package com.github.liyp.mapdb.test;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Store;

public class HTreeMapTest {

    public static void main(String[] args) throws Exception {

        DB db = DBMaker.newFileDB(new File("_db")).closeOnJvmShutdown()
                .transactionDisable().make();

        HTreeMap<Integer, Request> map = db.createHashMap("cache")
                .expireMaxSize(10000).expireStoreSize(0.01)
                .expireAfterWrite(1, TimeUnit.SECONDS).makeOrGet();
        Store store = Store.forDB(db);
        System.out.println(map.size());

        for (int i = 0; i < 100000; i++) {
            map.put(i, new Request("" + i, new Date()));
            if (i % 1000 == 0) {
                System.out.println("curr i = " + i);
            }
            if (store.getFreeSize() > 10000000) {
                long freeSize = store.getFreeSize();
                long currentSize = store.getCurrSize();
                System.out.println("freeSize:" + freeSize + ", currentSize:"
                        + currentSize);
                db.compact();
            }
        }

        System.out.println(map.size());
        System.out.println("get 1 :" + map.get(1));

        while (true) {
            map.get(100);
            Thread.sleep(5000);
            System.out.println(map.keySet());
            long freeSize = Store.forDB(db).getFreeSize();
            long currentSize = Store.forDB(db).getCurrSize();
            System.out.println(
                    "freeSize:" + freeSize + ", currentSize:" + currentSize);
        }
    }

    static class Request implements Serializable {
        private static final long serialVersionUID = 1L;
        String name;
        Date time;
        String str;

        public Request(String name, Date time) {
            this.name = name;
            this.time = time;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                sb.append('a');
            }
            this.str = sb.toString();
        }

        public String toString() {
            return name + "|" + time;
        }
    }

}
