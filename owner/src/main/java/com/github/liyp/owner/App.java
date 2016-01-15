package com.github.liyp.owner;

import org.aeonbits.owner.ConfigFactory;

public class App {
    public static void main(String[] args) {
        SystemEnvProperties cfg = ConfigFactory.create(
                SystemEnvProperties.class,
                System.getProperties(),
                System.getenv());

        System.out.println(cfg.home());
        while (true) {
            try {
                Thread.sleep(6000);
            } catch (Exception e) {
            }
            System.out.println(cfg.test());
        }
    }
}
