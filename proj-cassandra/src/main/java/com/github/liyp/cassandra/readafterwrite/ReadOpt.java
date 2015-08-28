package com.github.liyp.cassandra.readafterwrite;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.mapping.Result;

public class ReadOpt implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(ReadOpt.class);

    private RAWBeanAccessor acc;

    public ReadOpt(RAWBeanDao dao) {
        this.acc = dao.getMappingManager()
                .createAccessor(RAWBeanAccessor.class);
    }

    @Override
    public void run() {
        System.out.println("r." + WriteOpt.readyToWrite.get());
        if (WriteOpt.readyToWrite.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        Result<RAWBean> result = acc.getBean(WriteOpt.pk);
        System.out.println(String.format("accessor get pk %s. result size %d",
                WriteOpt.pk, result.all().size()));
        for (RAWBean bean : result) {
            if (bean.getClusteringKey().equals(WriteOpt.ck)) {
                if (!WriteOpt.data.equals(bean.getData())) {
                    System.out.println(String.format(
                            "R-A-W error. data %s | bean %s", WriteOpt.data,
                            bean));
                } else {
                    WriteOpt.readyToWrite.compareAndSet(false, true);
                    break;
                }
            }
        }
    }
}
