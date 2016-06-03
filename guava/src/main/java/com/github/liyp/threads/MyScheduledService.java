/*
 * Copyright © 2016 liyp (liyp.yunpeng@gmail.com)
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
package com.github.liyp.threads;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.AbstractScheduledService;

public class MyScheduledService extends AbstractScheduledService {

    static AtomicInteger cnt;

    protected void startUp() throws Exception {
        cnt = new AtomicInteger(0);
    }

    protected ScheduledExecutorService executor() {
        return new ScheduledThreadPoolExecutor(10) {
            @Override
            public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                    long initialDelay, long delay, TimeUnit unit) {
                return super.scheduleWithFixedDelay(command, initialDelay,
                        delay, unit);
            }
        };
    }

    @Override
    protected void runOneIteration() throws Exception {
        int i = cnt.incrementAndGet();
        System.out.println(
                "#B " + i + "  timestamp " + System.currentTimeMillis() / 1000);
        Random random = new Random(System.currentTimeMillis());
        long sleepTime = 200 * random.nextInt(100);
        Thread.sleep(sleepTime);
        System.out.println(
                "#E " + i + "  timestamp " + System.currentTimeMillis() / 1000);
    }

    @Override
    protected Scheduler scheduler() {
        // return Scheduler.newFixedDelaySchedule(1, 10, TimeUnit.SECONDS);
        return Scheduler.newFixedRateSchedule(1, 10, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        MyScheduledService ss = new MyScheduledService();
        ss.startAsync();
    }

}
