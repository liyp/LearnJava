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
