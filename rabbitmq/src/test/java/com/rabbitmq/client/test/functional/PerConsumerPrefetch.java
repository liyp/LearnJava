/*
 * Copyright © 2016 liyp (liyp.yunpeng@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rabbitmq.client.test.functional;

import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.test.BrokerTestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.rabbitmq.client.test.functional.QosTests.drain;

public class PerConsumerPrefetch extends BrokerTestCase {
    private String q;

    @Override
    protected void createResources() throws IOException {
        q = channel.queueDeclare().getQueue();
    }

    private interface Closure {
        public void makeMore(List<Delivery> deliveries) throws IOException;
    }

    public void testSingleAck() throws IOException {
        testPrefetch(new Closure() {
            public void makeMore(List<Delivery> deliveries) throws IOException {
                for (Delivery del : deliveries) {
                    ack(del, false);
                }
            }
        });
    }

    public void testMultiAck() throws IOException {
        testPrefetch(new Closure() {
            public void makeMore(List<Delivery> deliveries) throws IOException {
                ack(deliveries.get(deliveries.size() - 1), true);
            }
        });
    }

    public void testSingleNack() throws IOException {
        for (final boolean requeue: Arrays.asList(false, true)) {
            testPrefetch(new Closure() {
                public void makeMore(List<Delivery> deliveries) throws IOException {
                    for (Delivery del : deliveries) {
                        nack(del, false, requeue);
                    }
                }
            });
        }
    }

    public void testMultiNack() throws IOException {
        for (final boolean requeue: Arrays.asList(false, true)) {
            testPrefetch(new Closure() {
                public void makeMore(List<Delivery> deliveries) throws IOException {
                    nack(deliveries.get(deliveries.size() - 1), true, requeue);
                }
            });
        }
    }

    public void testRecover() throws IOException {
        testPrefetch(new Closure() {
            public void makeMore(List<Delivery> deliveries) throws IOException {
                channel.basicRecover();
            }
        });
    }

    private void testPrefetch(Closure closure) throws IOException {
        QueueingConsumer c = new QueueingConsumer(channel);
        publish(q, 15);
        consume(c, 5, false);
        List<Delivery> deliveries = drain(c, 5);

        ack(channel.basicGet(q, false), false);
        drain(c, 0);

        closure.makeMore(deliveries);
        drain(c, 5);
    }

    public void testPrefetchOnEmpty() throws IOException {
        QueueingConsumer c = new QueueingConsumer(channel);
        publish(q, 5);
        consume(c, 10, false);
        drain(c, 5);
        publish(q, 10);
        drain(c, 5);
    }

    public void testAutoAckIgnoresPrefetch() throws IOException {
        QueueingConsumer c = new QueueingConsumer(channel);
        publish(q, 10);
        consume(c, 1, true);
        drain(c, 10);
    }

    public void testPrefetchZeroMeansInfinity() throws IOException {
        QueueingConsumer c = new QueueingConsumer(channel);
        publish(q, 10);
        consume(c, 0, false);
        drain(c, 10);
    }

    private void publish(String q, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            channel.basicPublish("", q, null, "".getBytes());
        }
    }

    private void consume(QueueingConsumer c, int prefetch, boolean autoAck) throws IOException {
        channel.basicQos(prefetch);
        channel.basicConsume(q, autoAck, c);
    }

    private void ack(Delivery del, boolean multi) throws IOException {
        channel.basicAck(del.getEnvelope().getDeliveryTag(), multi);
    }

    private void ack(GetResponse get, boolean multi) throws IOException {
        channel.basicAck(get.getEnvelope().getDeliveryTag(), multi);
    }

    private void nack(Delivery del, boolean multi, boolean requeue) throws IOException {
        channel.basicNack(del.getEnvelope().getDeliveryTag(), multi, requeue);
    }
}
