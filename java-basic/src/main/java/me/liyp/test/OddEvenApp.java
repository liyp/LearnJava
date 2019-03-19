package me.liyp.test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OddEvenApp {

    private static volatile boolean running = true;

    public static void main1(String[] args) {

        AtomicLong i = new AtomicLong();
        ReentrantLock lock = new ReentrantLock();
        Condition aReady = lock.newCondition();
        Condition bReady = lock.newCondition();

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    lock.lock();
                    try {
                        while (i.get() % 2 != 0) {
                            aReady.await();
                        }
                        System.out.println("A: " + i.getAndIncrement());
                        bReady.signalAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    lock.lock();
                    try {
                        while (i.get() % 2 != 1) {
                            bReady.await();
                        }
                        System.out.println("B: " + i.getAndIncrement());
                        aReady.signalAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        System.out.println("start ..");
        a.start();
        b.start();
    }

    public static void main2(String[] args) {

        AtomicLong i = new AtomicLong(0);

        Thread a = new Thread(new Runnable() {
            long c = 0;

            @Override
            public void run() {
                while (true) {
                    while (!i.compareAndSet(c, c + 1)) {
                    }
                    System.out.println("A: " + c);
                    c += 2;
                }
            }
        });

        Thread b = new Thread(new Runnable() {
            long c = 1;

            @Override
            public void run() {
                while (true) {
                    while (!i.compareAndSet(c, c + 1)) {
                    }
                    System.out.println("B: " + c);
                    c += 2;
                }
            }

        });

        System.out.println("start ..");
        a.start();
        b.start();
    }

    public static void main(String[] args) {
        AtomicLong i = new AtomicLong(0);

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (i.get()%2==0) {
                        System.out.println("A: " + i.get());
                        i.incrementAndGet();
                    }
                }
            }
        });

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (i.get()%2==1) {
                        System.out.println("B: " + i.get());
                        i.incrementAndGet();
                    }
                }
            }

        });

        System.out.println("start ..");
        a.start();
        b.start();
    }
}
