package me.liyp.sort;

import java.util.Arrays;

public class SortApp {

    private static final int[] NUMBERS = { 49, 38, 65, 97, 76, 13, 27, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51 };

    // 直接插入排序
    private static int[] sort_insert() {
        int[] ns = NUMBERS.clone();

        for (int i = 1; i < ns.length; i++) {
            int plac = ns[i];
            int j = i - 1;
            for (; j >= 0 && ns[j] > plac; j--) {
                ns[j + 1] = ns[j];
            }
            ns[j + 1] = plac;
        }

        return ns;
    }

    // 希尔排序/递减增量排序算法 /非稳定排序算法
    // 1。 插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率
    // 2。 但插入排序一般来说是低效的，因为插入排序每次只能将数据移动一位
    private static int[] sort_shell() {
        int[] ns = NUMBERS.clone();

        int step = ns.length / 2;
        while(step >= 1) {
            for (int i = step; i < ns.length; i+=step) {
                int plac = ns[i];
                int j = i - step;
                for (; j >= 0 && ns[j] > plac; j-=step) {
                    ns[j + step] = ns[j];
                }
                ns[j + step] = plac;
            }
            step = step / 2;
        }

        return ns;
    }

    // 选择排序


    private static void cmp(String msg, int[] expect, int[] actual) {
        System.out.println("#=# " + msg);
        System.out.println(Arrays.toString(expect));
        System.out.println(Arrays.toString(actual));
        System.out.println(Arrays.equals(expect, actual));
        System.out.println();
    }

    public static void main(String[] args) {
        int[] ns = NUMBERS.clone();
        Arrays.sort(ns);
        cmp("insert sort", ns, sort_insert());
        cmp("shell sort", ns, sort_shell());
    }

}
