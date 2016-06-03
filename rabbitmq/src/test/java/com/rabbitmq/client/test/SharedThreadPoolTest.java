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
package com.rabbitmq.client.test;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQConnection;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class SharedThreadPoolTest extends BrokerTestCase {
    public void testWillShutDownExecutor() throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        ExecutorService executor = Executors.newFixedThreadPool(8);
        cf.setSharedExecutor(executor);

        AMQConnection conn1 = (AMQConnection)cf.newConnection();
        assertFalse(conn1.willShutDownConsumerExecutor());

        AMQConnection conn2 = (AMQConnection)cf.newConnection(Executors.newSingleThreadExecutor());
        assertFalse(conn2.willShutDownConsumerExecutor());

        AMQConnection conn3 = (AMQConnection)cf.newConnection((ExecutorService)null);
        assertTrue(conn3.willShutDownConsumerExecutor());

        cf.setSharedExecutor(null);

        AMQConnection conn4 = (AMQConnection)cf.newConnection();
        assertTrue(conn4.willShutDownConsumerExecutor());
    }
}
