package com.github.liyp.dubbo.consumer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifyImpl implements Notify {

    private static final Logger logger = LoggerFactory
            .getLogger(NotifyImpl.class);

    public Map<Integer, String> ret = new HashMap<>();
    public Map<Integer, Throwable> errors = new HashMap<>();

    @Override
    public void onreturn(String rst, Integer id) {
        logger.info("onreturn: rst={}, in={}", rst, id);
        ret.put(id, rst);
    }

    @Override
    public void onthrow(Throwable ex, Integer id) {
        logger.info("onthrow: ex={}, id={}", ex, id);
        errors.put(id, ex);
    }

}
