package com.github.liyp.rabbitmq.demo;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class Main {

    public static void main(String[] args) throws IOException,
            InterruptedException {
        // start spring context
        @SuppressWarnings({ "resource" })
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "com/github/liyp/rabbitmq/demo/applicationContext.xml");

        RabbitTemplate rabbitTemplate = (RabbitTemplate) context
                .getBean("rabbitTemplate");
        for (int i = 0; i < 200; i++) {
            rabbitTemplate.convertAndSend("queue_one", "test queue 1 " + i);
            rabbitTemplate.convertAndSend("queue_two", new MsgBean(
                    "test queue 2 " + i));
        }
        Thread.sleep(5000);
        ThreadPoolTaskExecutor threadExe = (ThreadPoolTaskExecutor) context
                .getBean("taskExecutor");
        System.out.println(threadExe.getActiveCount());
        System.out.println(threadExe.getCorePoolSize());
        System.out.println(threadExe.getMaxPoolSize());
        System.out.println(threadExe.getPoolSize());
        System.out.println(threadExe.getThreadPoolExecutor().getCorePoolSize());

        // System.out.println("rsv: " +
        // rabbitTemplate.receiveAndConvert("queue_two"));
    }

}
