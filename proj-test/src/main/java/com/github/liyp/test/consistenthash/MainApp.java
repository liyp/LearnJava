package com.github.liyp.test.consistenthash;

import java.util.HashSet;
import java.util.Set;

public class MainApp {

    private static final HashFunction HASH_FUNCTION = new HashFunction();

    public static void main(String[] args) {

        Set<String> nodes = new HashSet<String>();
        nodes.add("1");
        nodes.add("2");
        nodes.add("3");
        ConsistentHash<String> consistentHash = new ConsistentHash<String>(
                HASH_FUNCTION, 8, nodes);

        consistentHash.add("4");
        System.out.println(consistentHash.getSize() + "  "
                + consistentHash.toString());
        System.out.println(consistentHash.get("a5test111as11") + "  "
                + HASH_FUNCTION.hash("a5test111as11"));

        consistentHash.add("5");
        System.out.println(consistentHash.getSize() + "  "
                + consistentHash.toString());
        System.out.println(consistentHash.get("a5test111as11") + "  "
                + HASH_FUNCTION.hash("a5test111as11"));

        consistentHash.remove("1");
        System.out.println(consistentHash.getSize() + "  "
                + consistentHash.toString());
        System.out.println(consistentHash.get("a5test111as11") + "  "
                + HASH_FUNCTION.hash("a5test111as11"));
    }

}