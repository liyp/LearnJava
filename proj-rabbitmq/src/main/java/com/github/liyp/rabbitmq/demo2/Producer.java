package com.github.liyp.rabbitmq.demo2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("liyp");
        factory.setPassword("liyp");
        factory.setVirtualHost("/");
        factory.setHost("192.168.137.199");
        factory.setPort(5672);
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        for (int i = 0; i < 10000; i++) {
            byte[] messageBodyBytes = "Hello, world!".getBytes();
            channel.basicPublish("", "my-queue", null, messageBodyBytes);
            System.out.println(channel.isOpen());
        }

        channel.close();
        conn.close();
    }
}
