package com.github.liyp.dubbo.api;

import java.io.Serializable;

public class HelloBean implements Serializable {
    private static final long serialVersionUID = -4286925896508051698L;

    private Integer id;

    private String who;

    public HelloBean(Integer id, String who) {
        this.who = who;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String toString() {
        return who + "-" + id;
    }
}
