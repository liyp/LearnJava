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
package com.github.liyp.test.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liyunpeng on 3/3/16.
 */
public class LoggerTest {
    static Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    public static void main(String[] args) {
        logger.debug("test throwable {} {}", 1 ,12,new Exception("dasd"),new Exception("aaaa"));
        logger.error("test throwable {} {}", 1 ,12,new Exception("dasd"),new Exception("aaaa"));
    }
}
