package me.liyp.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SemaphoreApp {
    public static void main(String[] args) {
        Semaphore semp = new Semaphore(5);
        AtomicLong index = new AtomicLong();

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int j = 0; j < 10; j++) {
            executor.execute(new Runnable() {

                private final long i = index.incrementAndGet();

                @Override
                public void run() {
                    System.out.println(i + ".. start");
                    try {
                        semp.acquire();
                        try {
                            System.out.println(i + "... get lock");
                            TimeUnit.SECONDS.sleep(5);
                            System.out.println(i + "... exit");
                        } finally {
                            semp.release();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println("end.." + semp.getQueueLength());

        executor.shutdown();
    }
}
