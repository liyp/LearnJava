package com.github.liyp.threads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolExecutor {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 100; i++) {
            final int ii = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("i=" + ii);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("i=" + ii + " end.");
                }
            });
        }
        System.in.read();
        System.out.println("shutdown poolExecutor");
        executor.shutdown();
        System.out.println("shutdown poolExecutor end.");

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("after shudown, add new task");
            }
        });
    }

}
