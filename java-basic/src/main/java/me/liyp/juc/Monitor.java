package me.liyp.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Monitor {

    static final Object lock = new Object();
    static final CyclicBarrier barrier = new CyclicBarrier(3);
    static volatile boolean aloop = false;

    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            try {
                barrier.await();
                System.out.println("Thread A start...");
                TimeUnit.SECONDS.sleep(3);

                synchronized (lock) {
                    System.out.println("Thread A enter monitor...");
                    while(!aloop) {
                        System.out.println("Thread A wait monitor...");
                        lock.wait();
                        System.out.println("Thread A resurrect monitor...");
                    }
                    System.out.println("Thread A exit monitor...");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread A exit...");
            }
        });

        Thread b = new Thread(() -> {
            try {
                barrier.await();
                System.out.println("Thread B start...");
                TimeUnit.SECONDS.sleep(5);

                synchronized (lock) {
                    System.out.println("Thread B enter monitor...");
                    aloop = true;
                    lock.notifyAll();
                    System.out.println("Thread B notify...");
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("Thread B exit monitor...");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread B exit...");
            }
        });

        Thread c = new Thread(() -> {
            try {
                barrier.await();
                System.out.println("Thread C start...");
                TimeUnit.SECONDS.sleep(3);

                synchronized (lock) {
                    System.out.println("Thread C enter monitor...");

                    System.out.println("Thread C wait monitor...");
                    lock.wait(4000);
                    System.out.println("Thread C resurrect monitor...");

                    System.out.println("Thread C exit monitor...");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread C exit...");
            }
        });

        a.start();
        b.start();
        c.start();
    }
}
