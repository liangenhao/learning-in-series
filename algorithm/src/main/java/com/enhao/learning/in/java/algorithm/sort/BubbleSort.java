package com.enhao.learning.in.java.algorithm.sort;

/**
 * 冒泡排序算法
 * 1. 比较相邻的两个元素，如果前一个比后一个大，则交换位置
 * 2. 第一轮的时候，最后一个元素应该为最大的一个。
 * 3. 按照步骤一的方法进行相邻两个元素比较，这个时候由于最后一个元素已经是最大的了，所以最后一个元素不用比较
 *
 * @author enhao
 */
public class BubbleSort<T extends Comparable<T>> implements Sort<T> {

    @Override
    public void sort(T[] values) {
        // 第一层循环表示比较轮数:values.length - 1，
        for (int i = 0; i < values.length - 1; i++) {
            // 第二层循环结束时，最大的元素在最后一位。
            // 为什么第二层循环是values.length - 1 - i，因为第一层循环每轮后，最大的都在最后一位了，所以，最后一位没有必要比较了。
            for (int j = 0; j < values.length - 1 - i; j++) {
                if (values[j].compareTo(values[j + 1]) > 0) {
                    T temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }
            }
        }
    }

    // 优化
    public void optimizingSort(T[] values) {
        boolean flag = false;
        for (int i = 0; i < values.length - 1; i++) {
            flag = true;
            for (int j = 0; j < values.length - 1 - i; j++) {
                if (values[j].compareTo(values[j + 1]) > 0) {
                    T temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                    flag = false;
                }
            }
            // 如果某一趟没有元素移动，表示已经排序完，没必要再循环了
            if (flag) {
                break;
            }
        }
    }
}
