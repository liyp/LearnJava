/*
 * Copyright © 2017 liyp (liyp.yunpeng@gmail.com)
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

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.test.BrokerTestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConsumerPriorities extends BrokerTestCase {
    public void testValidation() throws IOException {
        assertFailValidation(args("banana"));
        assertFailValidation(args(new HashMap()));
        assertFailValidation(args(null));
        assertFailValidation(args(Arrays.asList(1, 2, 3)));
    }

    private void assertFailValidation(Map<String, Object> args) throws IOException {
        Channel ch = connection.createChannel();
        String queue = ch.queueDeclare().getQueue();
        try {
            ch.basicConsume(queue, true, args, new QueueingConsumer(ch));
            fail("Validation should fail for " + args);
        } catch (IOException ioe) {
            checkShutdownSignal(AMQP.PRECONDITION_FAILED, ioe);
        }
    }

    private static final int COUNT = 10;

    public void testConsumerPriorities() throws Exception {
        String queue = channel.queueDeclare().getQueue();
        QueueingConsumer highConsumer = new QueueingConsumer(channel);
        QueueingConsumer medConsumer = new QueueingConsumer(channel);
        QueueingConsumer lowConsumer = new QueueingConsumer(channel);
        String high = channel.basicConsume(queue, true, args(1), highConsumer);
        String med = channel.basicConsume(queue, true, medConsumer);
        channel.basicConsume(queue, true, args(-1), lowConsumer);

        publish(queue, COUNT, "high");
        channel.basicCancel(high);
        publish(queue, COUNT, "med");
        channel.basicCancel(med);
        publish(queue, COUNT, "low");

        assertContents(highConsumer, COUNT, "high");
        assertContents(medConsumer, COUNT, "med");
        assertContents(lowConsumer, COUNT, "low");
    }

    private Map<String, Object> args(Object o) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x-priority", o);
        return map;
    }

    private void assertContents(QueueingConsumer qc, int count, String msg) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            QueueingConsumer.Delivery d = qc.nextDelivery();
            assertEquals(msg, new String(d.getBody()));
        }
        assertEquals(null, qc.nextDelivery(0));
    }

    private void publish(String queue, int count, String msg) throws IOException {
        for (int i = 0; i < count; i++) {
            channel.basicPublish("", queue, MessageProperties.MINIMAL_BASIC, msg.getBytes());
        }
    }
}
