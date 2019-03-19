package me.liyp.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {

        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }

        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println(System.getProperty("java.class.path"));

        List<Long> l1 = new ArrayList<>();
        l1.add(1L);
        l1.add(2L);
        l1.add(3L);
        l1.add(1L);
        l1.add(1L);

        List<Long> l2 = new ArrayList<>();
        l2.add(3L);
        l2.add(1L);

        l1.removeAll(l2);
        System.out.println(l1);

        Pattern markPositionConfPattern = Pattern.compile("(\\s*[0-9]+\\s*,)+\\s*");
        System.out.println(markPositionConfPattern.matcher("  121, 221, 11 ,").matches());
    }
}
