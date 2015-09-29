package com.github.liyp.dubbo.consumer;

import com.github.liyp.dubbo.api.HelloBean;

public interface Notify {

    public void onreturn(String ret, HelloBean id);

    public void onthrow(Throwable ex, HelloBean id);
}