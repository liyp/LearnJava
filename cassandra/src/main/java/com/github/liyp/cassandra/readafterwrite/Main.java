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
