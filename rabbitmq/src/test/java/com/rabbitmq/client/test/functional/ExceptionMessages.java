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

import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.test.BrokerTestCase;

import java.io.IOException;
import java.util.UUID;

public class ExceptionMessages extends BrokerTestCase {
    public void testAlreadyClosedExceptionMessageWithChannelError() throws IOException {
        String uuid = UUID.randomUUID().toString();
        try {
            channel.queueDeclarePassive(uuid);
            fail("expected queueDeclarePassive to throw");
        } catch (IOException e) {
            // ignored
        }

        try {
            channel.queueDeclarePassive(uuid);
            fail("expected queueDeclarePassive to throw");
        } catch (AlreadyClosedException ace) {
            assertTrue(ace.getMessage().startsWith("channel is already closed due to channel error"));
        }
    }

    public void testAlreadyClosedExceptionMessageWithCleanClose() throws IOException {
        String uuid = UUID.randomUUID().toString();

        try {
            channel.abort();
            channel.queueDeclare(uuid, false, false, false, null);
        } catch (AlreadyClosedException ace) {
            assertTrue(ace.getMessage().startsWith("channel is already closed due to clean channel shutdown"));
        }
    }
}
