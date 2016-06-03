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
package com.github.liyp.dubbo.consumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.liyp.dubbo.api.HelloBean;
import com.github.liyp.dubbo.api.HelloService;

public class Consumer {

    static AtomicInteger ai = new AtomicInteger();
    static CountDownLatch cdl;
    static int limit = 5;
    static boolean locked = false;

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "com/github/liyp/dubbo/consumer/consumer.xml" });
        context.start();
        NotifyImpl notify = (NotifyImpl) context.getBean("callback");
        HelloService service = (HelloService) context.getBean("service");
        System.out.println("dubbo consumer running...");
        int i = 0;
        while (true) {
            String res = service.sayHello(new HelloBean(i, "lee"));
            // service.sayHello(i);
            System.out.println(ai.incrementAndGet());
            if (ai.get() >= limit) {
                locked = true;
                cdl = new CountDownLatch(limit);
                cdl.await();
                locked = false;
            }
            // Thread.sleep(1000);
            i++;
        }
    }
}