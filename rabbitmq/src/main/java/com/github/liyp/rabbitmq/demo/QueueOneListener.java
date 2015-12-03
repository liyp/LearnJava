package com.github.liyp.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class QueueOneListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ONE# Listener data :" + message.toString()
                + "thread : " + Thread.currentThread().toString());
    }
}