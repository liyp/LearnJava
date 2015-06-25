package com.github.liyp.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class QueueOneListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("Listener data :" + message.toString());
    }
}