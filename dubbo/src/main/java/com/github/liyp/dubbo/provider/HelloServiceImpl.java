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
package com.github.liyp.dubbo.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.liyp.dubbo.api.HelloBean;
import com.github.liyp.dubbo.api.HelloService;

public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory
            .getLogger(HelloServiceImpl.class);

    @Override
    public String sayHello(HelloBean who) {
        logger.info("Service: Say Hello {}", who);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello " + who;
    }

    @Override
    public String sayHello(int i) {
        logger.info("Service: Say Hello {}", i);
        return "hello " + i;
    }

}
