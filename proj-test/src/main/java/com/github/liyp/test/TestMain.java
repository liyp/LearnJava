package com.github.liyp.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;

public class TestMain {
    
    public static String[] splitAndTrim(String commaStr, String regex) {
        List<String> list = new ArrayList<>();
        for(String item : commaStr.split(",")) {
            list.add(item.trim());
        }
        return list.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        System.out.println(args.length);
        Iterator<String> iterator1 = IteratorUtils.arrayIterator(new String[] {
                "one", "two", "three", "11", "22", "AB" });
        Iterator<String> iterator2 = IteratorUtils.arrayIterator(new String[] {
                "a", "b", "c", "33", "ab", "aB" });

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
        
        System.out.println(ArrayUtils.toString(splitAndTrim(" 11, 21,12 ,", ",")));
        
        System.out.println(new ArrayList<String>().toString());
        
        JSONObject json = new JSONObject("{\"keynull\":null}");
        json.put("keya", "as");
        json.put("key2", 2212222222222222222L);
        System.out.println(json);
        System.out.println(json.get("keynull").equals(null));
        
        
        
    }
}
