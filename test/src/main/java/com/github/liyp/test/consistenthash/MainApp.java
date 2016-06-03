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