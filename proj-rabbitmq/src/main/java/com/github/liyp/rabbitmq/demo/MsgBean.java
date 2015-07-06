package com.github.liyp.rabbitmq.demo;

public class MsgBean {

    private String msg;

    public MsgBean() {
    }

    public MsgBean(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return msg;
    }
}
