package com.github.liyp.dubbo.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.liyp.dubbo.api.HelloService;

public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory
            .getLogger(HelloServiceImpl.class);

    @Override
    public String sayHello(Integer who) {
        logger.info("Service: Say Hello {}", who);
        return "Hello " + who;
    }

}
