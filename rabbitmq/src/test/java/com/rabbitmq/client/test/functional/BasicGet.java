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

import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.test.BrokerTestCase;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BasicGet extends BrokerTestCase {
  public void testBasicGetWithEnqueuedMessages() throws IOException, InterruptedException {
    assertTrue(channel.isOpen());
    String q = channel.queueDeclare().getQueue();

    basicPublishPersistent("msg".getBytes("UTF-8"), q);
    Thread.sleep(250);

    assertNotNull(channel.basicGet(q, true));
    channel.queuePurge(q);
    assertNull(channel.basicGet(q, true));
    channel.queueDelete(q);
  }

  public void testBasicGetWithEmptyQueue() throws IOException, InterruptedException {
    assertTrue(channel.isOpen());
    String q = channel.queueDeclare().getQueue();

    assertNull(channel.basicGet(q, true));
    channel.queueDelete(q);
  }

  public void testBasicGetWithClosedChannel() throws IOException, InterruptedException, TimeoutException {
    assertTrue(channel.isOpen());
    String q = channel.queueDeclare().getQueue();

    channel.close();
    assertFalse(channel.isOpen());
    try {
      channel.basicGet(q, true);
      fail("expected basic.get on a closed channel to fail");
    } catch (AlreadyClosedException e) {
      // passed
    } finally {
      Channel tch = connection.createChannel();
      tch.queueDelete(q);
      tch.close();
    }

  }
}
