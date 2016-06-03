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
package com.github.liyp.test;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {

    public static String[] splitAndTrim(String commaStr, String regex) {
        List<String> list = new ArrayList<>();
        for (String item : commaStr.split(",")) {
            list.add(item.trim());
        }
        return list.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // add a shutdown hook to stop the server
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("########### shoutdown begin....");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("########### shoutdown end....");
            }
        }));

        System.out.println(args.length);
        Iterator<String> iterator1 = IteratorUtils.arrayIterator(new String[]{
                "one", "two", "three", "11", "22", "AB"});
        Iterator<String> iterator2 = IteratorUtils.arrayIterator(new String[]{
                "a", "b", "c", "33", "ab", "aB"});

        Iterator<String> chainedIter = IteratorUtils.chainedIterator(iterator1,
                iterator2);

        System.out.println("==================");

        Iterator<String> iter = IteratorUtils.filteredIterator(chainedIter,
                new Predicate() {
                    @Override
                    public boolean evaluate(Object arg0) {
                        System.out.println("xx:" + arg0.toString());
                        String str = (String) arg0;
                        return str.matches("([a-z]|[A-Z]){2}");
                    }
                });
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        System.out.println("===================");

        System.out.println("asas".matches("[a-z]{4}"));

        System.out.println("Y".equals(null));

        System.out.println(String.format("%02d", 1000L));

        System.out.println(ArrayUtils
                .toString(splitAndTrim(" 11, 21,12 ,", ",")));

        System.out.println(new ArrayList<String>().toString());

        JSONObject json = new JSONObject("{\"keynull\":null}");
        json.put("bool", false);
        json.put("keya", "as");
        json.put("key2", 2212222222222222222L);
        System.out.println(json);
        System.out.println(json.get("keynull").equals(null));

        String a = String
                .format("{\"id\":%d,\"method\":\"testCrossSync\","
                                + "\"circle\":%d},\"isEnd\":true",
                        1, 1);
        System.out.println(a.getBytes().length);

        System.out.println(new String[]{"a", "b"});

        System.out.println(new JSONArray("[\"aa\",\"\"]"));

        String data = String.format("%9d %s", 1,
                RandomStringUtils.randomAlphanumeric(10));
        System.out.println(data.getBytes().length);

        System.out
                .println(ArrayUtils.toString("1|2| 3|  333||| 3".split("\\|")));

        JSONObject j1 = new JSONObject("{\"a\":\"11111\"}");
        JSONObject j2 = new JSONObject(j1.toString());
        j2.put("b", "22222");
        System.out.println(j1 + " | " + j2);

        System.out.println("======================");

        String regex = "\\d+(\\-\\d+){2} \\d+(:\\d+){2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("2015-12-28 15:46:14  付_NC250_MD旧固件:motion de\n");
        String eventDate = matcher.find() ? matcher.group() : "";

        System.out.println(eventDate);
    }
}
