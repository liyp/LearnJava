package com.github.liyp.dubbo.api;

public interface HelloService extends Service {

    public String sayHello(HelloBean bean);

    public String sayHello(int i);

}
