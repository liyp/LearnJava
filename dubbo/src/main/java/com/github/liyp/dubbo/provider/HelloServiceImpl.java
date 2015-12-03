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
