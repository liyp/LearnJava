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
package com.rabbitmq.client.test.server;

import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.test.BrokerTestCase;
import com.rabbitmq.client.test.functional.DeadLetterExchange;
import com.rabbitmq.tools.Host;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeadLetterExchangeDurable extends BrokerTestCase {
    @Override
    protected void createResources() throws IOException {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", 5000);
        args.put("x-dead-letter-exchange", DeadLetterExchange.DLX);

        channel.exchangeDeclare(DeadLetterExchange.DLX, "direct", true);
        channel.queueDeclare(DeadLetterExchange.DLQ, true, false, false, null);
        channel.queueDeclare(DeadLetterExchange.TEST_QUEUE_NAME, true, false, false, args);
        channel.queueBind(DeadLetterExchange.TEST_QUEUE_NAME, "amq.direct", "test");
        channel.queueBind(DeadLetterExchange.DLQ, DeadLetterExchange.DLX, "test");
    }

    @Override
    protected void releaseResources() throws IOException {
        channel.exchangeDelete(DeadLetterExchange.DLX);
        channel.queueDelete(DeadLetterExchange.DLQ);
        channel.queueDelete(DeadLetterExchange.TEST_QUEUE_NAME);
    }

    public void testDeadLetterQueueTTLExpiredWhileDown() throws Exception {
        // This test is nonsensical (and often breaks) in HA mode.
        if (HATests.HA_TESTS_RUNNING) return;

        for(int x = 0; x < DeadLetterExchange.MSG_COUNT; x++) {
            channel.basicPublish("amq.direct", "test", MessageProperties.MINIMAL_PERSISTENT_BASIC, "test message".getBytes());
        }

        closeConnection();
        Host.invokeMakeTarget("stop-app");
        Thread.sleep(5000);
        Host.invokeMakeTarget("start-app");
        openConnection();
        openChannel();

        //This has the effect of waiting for the queue to complete the
        //dead lettering. Some raciness remains though since the
        //dead-letter publication is async so the 'consume' below may
        //reach the dlq before all dead-lettered messages have arrived
        //there.
        assertNull(basicGet(DeadLetterExchange.TEST_QUEUE_NAME));

        DeadLetterExchange.consume(channel, "expired");
    }
}
