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

import java.io.IOException;

public class PersistenceGuarantees extends BrokerTestCase {
    private static final int COUNT = 10000;
    private String queue;

    protected void declareQueue() throws IOException {
        queue = channel.queueDeclare("", true, false, false, null).getQueue();
    }

    public void testTxPersistence() throws Exception {
        declareQueue();
        channel.txSelect();
        publish();
        channel.txCommit();
        restart();
        assertPersisted();
    }

    public void testConfirmPersistence() throws Exception {
        declareQueue();
        channel.confirmSelect();
        publish();
        channel.waitForConfirms();
        restart();
        assertPersisted();
    }

    private void assertPersisted() throws IOException {
        assertEquals(COUNT, channel.queueDelete(queue).getMessageCount());
    }

    private void publish() throws IOException {
        for (int i = 0; i < COUNT; i++) {
            channel.basicPublish("", queue, false, false, MessageProperties.PERSISTENT_BASIC, "".getBytes());
        }
    }
}
