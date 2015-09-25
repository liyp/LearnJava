package com.github.liyp.dubbo.consumer;

public interface Notify {

    public void onreturn(String ret, Integer id);

    public void onthrow(Throwable ex, Integer id);
}