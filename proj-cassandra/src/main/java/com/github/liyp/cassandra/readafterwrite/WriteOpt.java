package com.github.liyp.cassandra.readafterwrite;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteOpt implements Runnable {

    private final static Logger logger = LoggerFactory
            .getLogger(WriteOpt.class);

    public static volatile AtomicBoolean readyToWrite = new AtomicBoolean(true);

    public static volatile String pk;
    public static volatile String ck;
    public static volatile String data;
    public static volatile Date date;

    private RAWBeanDao dao;

    public WriteOpt(RAWBeanDao dao) {
        this.dao = dao;
    }

    private void write() {
        pk = RandomStringUtils.randomNumeric(9);
        ck = RandomStringUtils.randomAlphabetic(9);
        data = "data_" + RandomStringUtils.randomAlphanumeric(30);
        date = new Date();
        RAWBean bean = new RAWBean(pk, ck, data, date);
        dao.getMappingManager().mapper(RAWBean.class).save(bean);
    }

    @Override
    public void run() {
        System.out.println("w." + readyToWrite.get());
        if (readyToWrite.get()) {
            write();
            readyToWrite.compareAndSet(true, false);
        } else {
            RAWBean bean = dao.getMappingManager().mapper(RAWBean.class)
                    .get(pk, ck);
            if (bean == null || !bean.getData().equals(data)) {
                System.out.println(String.format(
                        "R-A-W error. curr pk=%s ck=%s data=%s date=%s | {}",
                        bean));
            }
        }
    }

}