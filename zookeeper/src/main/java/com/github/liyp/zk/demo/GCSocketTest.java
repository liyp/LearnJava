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

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by liyunpeng on 5/26/16.
 */
public class GCSocketTest {

    private static final HashMap<String, Object> map = new HashMap<>();

    private static ZkElector zk;


    static class Node_16M {
        Integer[][][] data = new Integer[1024][512][16];
    }

    static class Node_1M {
        Integer[][] data = new Integer[1024][512];
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        int i = 0;
        while (true) {
            i++;
            map.put("" + i, new Node_1M());
            if (i >= 350) {
                break;
            }
        }

        zk = new ZkElector("127.0.0.1", 5000);

        System.out.println("Max mem: " + Runtime.getRuntime().maxMemory());

        i = 100;
        while (true) {
            Thread.sleep(100);
            Runtime.getRuntime().gc();
            if (i - 100 <= 100) {
                i++;
                map.put("" + i, new Node_1M());
            } else {
                new Node_1M();
                System.out.println("Max mem: " + Runtime.getRuntime().maxMemory());
                System.out.println("Free mem: " + Runtime.getRuntime().freeMemory());
                Thread.sleep(100);
                //Runtime.getRuntime().gc();
            }
        }
    }
}
