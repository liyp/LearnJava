package com.github.liyp.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.liyp.dubbo.api.HelloService;

public class Consumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "com/github/liyp/dubbo/consumer/consumer.xml" });
        context.start();
        NotifyImpl notify = (NotifyImpl) context.getBean("callback");
        HelloService service = (HelloService) context.getBean("service");
        System.out.println("dubbo consumer running...");
        int i = 0;
        while (true) {
            String res = service.sayHello(i);
            System.out.println(res);
            System.out.println(notify.ret.containsKey(i));
            Thread.sleep(10000);
            i++;
        }
    }
}