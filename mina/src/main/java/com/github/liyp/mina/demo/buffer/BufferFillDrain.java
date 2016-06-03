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
package com.github.liyp.mina.demo.buffer;

import java.nio.CharBuffer;

/**
 * http://my.oschina.net/ielts0909/blog/84876
 */
public class BufferFillDrain {
    private static int index = 0;
    private static String[] strings = { "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees" };

    private static boolean fillBuffer(CharBuffer buffer) {
        if (index >= strings.length) {
            return false;
        }
        String string = strings[index++];

        for (int i = 0; i < string.length(); i++) {
            buffer.put(string.charAt(i));
        }
        return true;
    }

    private static void drainBuffer(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        System.err.println("");
    }

    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(100);
        while (fillBuffer(buffer)) {
            buffer.flip();
            drainBuffer(buffer);
            buffer.clear();
        }
    }
}
