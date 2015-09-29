package com.github.liyp.dubbo.consumer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.liyp.dubbo.api.HelloBean;

public class NotifyImpl implements Notify {

    private static final Logger logger = LoggerFactory
            .getLogger(NotifyImpl.class);

    public Map<Integer, String> ret = new HashMap<>();
    public Map<Integer, Throwable> errors = new HashMap<>();

    @Override
    public void onreturn(String rst, HelloBean id) {
        logger.info("onreturn: rst={}, in={}", rst, id);
        ret.put(id.getId(), rst);
    }

    @Override
    public void onthrow(Throwable ex, HelloBean id) {
        logger.info("onthrow: ex={}, id={}", ex, id);
        errors.put(id.getId(), ex);
    }

}
