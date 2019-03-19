package me.liyp.juc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLockApp
 */
public class ReentrantLockApp {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Runnable r = new Runnable() {

            @Override
            public void run() {
                System.out.println("start..." + Thread.currentThread().getName());
                System.out.println("lock..." + Thread.currentThread().getName());
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println("get lock...sleep 3s" + Thread.currentThread().getName());
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println("exit..." + Thread.currentThread().getName());
            }
        };

        Thread a = new Thread(r);
        Thread b = new Thread(r);
        Thread c = new Thread(r);

        lock.lock();

        a.start();
        b.start();
        c.start();

        a.interrupt();

        lock.unlock();
    }
}