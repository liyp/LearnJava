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
    private static int[] sort_selection() {
        int[] ns = NUMBERS.clone();

        for (int i = 0; i < ns.length; i++) {
            int cmp = ns[i];
            int cmp_idx = i;
            for (int j = i+1; j < ns.length; j++) {
                if (ns[j] < cmp) {
                    cmp_idx = j;
                    cmp = ns[j];
                }
            }
            ns[cmp_idx] = ns[i];
            ns[i] = cmp;
        }

        return ns;
    }

    // 堆排序
    // 1. 最大堆调整 2. 创建最大堆 3. 排序
    private static int[] sort_heap() {
        int[] ns = NUMBERS.clone();

        int from = (ns.length-2) / 2;
        for (; from>=0; from--) {
            maxHeap(from, ns.length-1, ns);
        }

        // 排序
        for (int i=ns.length-1; i>0; i--) {
            swap(ns, 0, i);
            maxHeap(0, i-1, ns);
        }
        return ns;
    }

    // 最大堆调整
    private static void maxHeap(int idx, int len, int[] ns) {
        int cl_idx = idx * 2 + 1;
        int cr_idx = idx * 2 + 2;
        int max_idx = idx;

        if (cr_idx <= len && ns[cr_idx] > ns[cl_idx]) {
            max_idx = cr_idx;
        } else if (cl_idx <= len) {
            max_idx = cl_idx;
        }

        // 比较子和父节点的大小
        // 替换节点后，需要对替换了的子节点递归判断其是否符合堆逻辑
        if (ns[max_idx] > ns[idx]) {
            swap(ns, max_idx, idx);
            maxHeap(max_idx, len, ns);
        }
    }

    private static void swap(int[] ns, int l, int r) {
        int tmp = ns[l];
        ns[l] = ns[r];
        ns[r] = tmp;
    }


    // 冒泡排序
    private static int[] sort_bubble() {
        int[] ns = NUMBERS.clone();

        for (int i=0; i<ns.length; i++) {
            for (int j=0; j<ns.length-1-i; j++) {
                if (ns[j] > ns[j+1]) {
                    swap(ns, j, j+1);
                }
            }
        }

        return ns;
    }

    // 快速排序 冒泡+二分+递归分治
    private static int[] sort_quick() {
        int[] ns = NUMBERS.clone();
        _quick_sort(ns, 0, ns.length-1);
        return ns;
    }
    private static void _quick_sort(int[] ns, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = _quick_mid(ns, start, end);
        _quick_sort(ns, start, mid-1);
        _quick_sort(ns, mid+1, end);
    }
    private static int _quick_mid(int[] ns, int start, int end) {
        int tmp = ns[start];
        for (;start < end;) {
            for (;start < end && ns[end]>=tmp;end--) {}
            ns[start] = ns[end];
            for (;start < end && ns[start]<=tmp;start++) {}
            ns[end] = ns[start];
        }
        ns[start] = tmp;
        return start;
    }

    // 合并排序
    private static int[] sort_merge() {
        int[] ns = NUMBERS.clone();
        _merge(ns, 0, ns.length-1);
        return ns;
    }
    private static void _merge(int[] ns, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        _merge(ns, start, mid);
        _merge(ns, mid+1, end);
        int i = start;
        int j = mid+1;
        int nss_i = 0;
        int[] nss = new int[end-start+1];
        for (;j<=end || i<=mid;) {
            for (;i<=mid && (j>end || ns[i]<=ns[j]);) {
                nss[nss_i++] = ns[i++];
            }
            for (;j<=end && (i>mid || ns[j]<=ns[i]);){
                nss[nss_i++] = ns[j++];
            }
        }
        for (nss_i=0; nss_i<nss.length; nss_i++) {
            ns[nss_i+start] = nss[nss_i];
        }
    }

    // 计数排序
    private static int[] sort_count() {
        int[] ns = NUMBERS.clone();

        int max = Integer.MIN_VALUE;
        for (int i : ns) {
            if (max < i) {
                max = i;
            }
        }
        int[] count = new int[max+1];
        for (int i=0; i<ns.length; i++) {
            count[ns[i]]++;
        }

        int ii = 0;
        for (int i=0; i<=max; i++) {
            for (int j=0; j<count[i]; j++) {
                ns[ii++] = i;
            }
        }

        return ns;
    }


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
        cmp("selection sort", ns, sort_selection());
        cmp("heap sort", ns, sort_heap());
        cmp("bubble sort", ns, sort_bubble());
        cmp("quick sort", ns, sort_quick());
        cmp("merge sort", ns, sort_merge());
        cmp("count sort", ns, sort_count());
    }

}
