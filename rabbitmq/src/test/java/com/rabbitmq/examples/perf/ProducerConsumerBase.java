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
package com.rabbitmq.examples.perf;

/**
 *
 */
public class ProducerConsumerBase {
    protected float rateLimit;
    protected long  lastStatsTime;
    protected int   msgCount;

    protected void delay(long now) {

        long elapsed = now - lastStatsTime;
        //example: rateLimit is 5000 msg/s,
        //10 ms have elapsed, we have sent 200 messages
        //the 200 msgs we have actually sent should have taken us
        //200 * 1000 / 5000 = 40 ms. So we pause for 40ms - 10ms
        long pause = (long) (rateLimit == 0.0f ?
            0.0f : (msgCount * 1000.0 / rateLimit - elapsed));
        if (pause > 0) {
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
