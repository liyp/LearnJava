package com.github.liyp.rabbitmq.demo;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws IOException {
        // start spring context
        @SuppressWarnings({ "resource" })
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "com/github/liyp/rabbitmq/demo/applicationContext.xml");

        MyMqGatway mq = (MyMqGatway) context.getBean("myMqGatway");
        mq.sendDataToCrQueue("test");
    }

}
