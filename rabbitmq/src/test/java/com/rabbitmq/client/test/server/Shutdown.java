package com.rabbitmq.client.test.server;

import com.rabbitmq.client.test.BrokerTestCase;
import com.rabbitmq.client.AMQP;

public class Shutdown extends BrokerTestCase {

    public void testErrorOnShutdown() throws Exception {
        bareRestart();
        expectError(AMQP.CONNECTION_FORCED);
    }

}
