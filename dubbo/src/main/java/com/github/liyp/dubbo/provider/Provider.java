package com.github.liyp.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "com/github/liyp/dubbo/provider/provider.xml" });
        context.start();
        System.out.println("dubbo provider running...");
        System.in.read();
    }
}