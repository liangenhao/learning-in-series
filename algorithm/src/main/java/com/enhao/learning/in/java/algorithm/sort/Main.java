package com.enhao.learning.in.java.algorithm.sort;

import java.util.Arrays;

/**
 * 排序算法
 *
 * @author enhao
 */
public class Main {

    public static void main(String[] args) {
        Integer[] values = Sort.of(3, 1, 2, 5, 4);

        // 冒泡排序
        // Sort<Integer> sort = new BubbleSort<>();
        // 选择排序
        // Sort<Integer> sort = new SelectionSort<>();
        // 插入排序
        Sort<Integer> sort = new InsertionSort<>();
        sort.sort(values);
        System.out.println(Arrays.toString(values));
    }
}
