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
package com.github.liyp.dubbo.consumer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.liyp.dubbo.api.HelloBean;

public class NotifyImpl implements Notify {

    private static final Logger logger = LoggerFactory.getLogger(NotifyImpl.class);

    public Map<Integer, String> ret = new HashMap<>();
    public Map<Integer, Throwable> errors = new HashMap<>();

    @Override
    public void onreturn(String rst, HelloBean id) {
        logger.info("onreturn: rst={}, in={}", rst, id);
        ret.put(id.getId(), rst);
        Consumer.ai.decrementAndGet();
        if (Consumer.locked) {
            Consumer.cdl.countDown();
        }
    }

    @Override
    public void onthrow(Throwable ex, HelloBean id) {
        logger.info("onthrow: ex={}, id={}", ex, id);
        errors.put(id.getId(), ex);
        Consumer.ai.decrementAndGet();
        if (Consumer.locked) {
            Consumer.cdl.countDown();
        }
    }

}
