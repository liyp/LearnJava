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
package com.github.liyp.rabbitmq.demo;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class Main {

    public static void main(String[] args) throws IOException,
            InterruptedException {
        // start spring context
        @SuppressWarnings({ "resource" })
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "com/github/liyp/rabbitmq/demo/applicationContext.xml");

        RabbitTemplate rabbitTemplate = (RabbitTemplate) context
                .getBean("rabbitTemplate");
        for (int i = 0; i < 200; i++) {
            rabbitTemplate.convertAndSend("queue_one", "test queue 1 " + i);
            rabbitTemplate.convertAndSend("queue_two", new MsgBean(
                    "test queue 2 " + i));
        }
        Thread.sleep(5000);
        ThreadPoolTaskExecutor threadExe = (ThreadPoolTaskExecutor) context
                .getBean("taskExecutor");
        System.out.println(threadExe.getActiveCount());
        System.out.println(threadExe.getCorePoolSize());
        System.out.println(threadExe.getMaxPoolSize());
        System.out.println(threadExe.getPoolSize());
        System.out.println(threadExe.getThreadPoolExecutor().getCorePoolSize());

        // System.out.println("rsv: " +
        // rabbitTemplate.receiveAndConvert("queue_two"));
    }

}
