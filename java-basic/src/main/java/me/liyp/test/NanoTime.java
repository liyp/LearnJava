package me.liyp.test;

import java.util.concurrent.TimeUnit;

public class NanoTime {
    public static class Clock {
        long st = System.currentTimeMillis()*1000*1000;
        long na = System.nanoTime();
    }

    public static void main(String[] args) {
        Clock pre = new Clock(), curr;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            curr = new Clock();
            System.out.println("Curr st="+curr.st+"; na="+curr.na+"; diff="+(curr.na-pre.na));
            pre = curr;
        }
    }
}
