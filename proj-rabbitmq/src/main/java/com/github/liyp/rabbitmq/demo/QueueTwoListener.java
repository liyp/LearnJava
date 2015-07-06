package com.github.liyp.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class QueueTwoListener implements MessageListener {

    private RabbitTemplate rabbitTemplate;

    @Override
    public void onMessage(Message message) {
        Object obj = rabbitTemplate.getMessageConverter().fromMessage(message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TWO# Listener data :" + message.toString()
                + "obj :" + obj + " " + obj.getClass() + " thread : "
                + Thread.currentThread().toString());
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}