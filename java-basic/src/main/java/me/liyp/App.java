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
package me.liyp;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 *
 */
public class App implements Runnable {

    final static Object lock = new Object();

    static App app;

    static App getApp() {
        if (app == null) {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getId());
                if (app == null) {
                    app = new App();
                }
            }
        }
        return app;
    }

    static long a;

    void set(long aa) {
        a = aa;
    }

    long get() {
        return a;
    }

    private static final AtomicInteger next = new AtomicInteger(1);
    private long i = -111;

    public App() {
        int id = next.getAndIncrement();
        i = id%2==0?id:((long)id)<<32;
        System.out.println("##"+Thread.currentThread().getId()+" "+i);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("##"+Thread.currentThread().getId()+" end");
    }

    void handle() {
        long s = System.currentTimeMillis();
        while (true) {
            set(i);
            long l = get();
            if (l>>32 != 0 && (l&(0xffffffffL)) !=0) {
                System.out.println("#"+Thread.currentThread().getId()+" "+i + " get=" + l);
            }
            if (System.currentTimeMillis()-s > 60000) {
                break;
            }
        }
    }

    static App appp;


    static void initApp() {
        appp = new App();
    }

    public void run() {
        App.getApp();
    }

    public static void main( String[] args ) {
        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println("#"+Thread.currentThread().getId());
                    int i = 0;
                    while(true) {
                        i++;
                        if (App.appp != null) {
                            System.out.println("#"+Thread.currentThread().getId()+" "+App.appp.i);
                        }
                        //synchronized (lock) { }
                        //if (i>600000) break;
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        App.initApp();
    }
}
