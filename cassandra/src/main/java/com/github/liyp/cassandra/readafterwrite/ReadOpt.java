/*
 * Copyright © 2017 liyp (liyp.yunpeng@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
