package com.github.liyp.rabbitmq.demo;

import org.springframework.amqp.core.AmqpTemplate;

public class MyMqGatway {

    private AmqpTemplate amqpTemplate;

    public void sendDataToCrQueue(Object obj) {
        amqpTemplate.convertAndSend("queue_one_key", obj);
    }

    public AmqpTemplate getAmqpTemplate() {
        return amqpTemplate;
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }
}
