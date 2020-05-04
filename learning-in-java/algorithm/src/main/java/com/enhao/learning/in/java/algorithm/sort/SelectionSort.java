package com.enhao.learning.in.java.algorithm.sort;

/**
 * 选择排序
 * 首先，在未排序序列中选择最小（大）元素，存放到排序序列的起始位置。
 * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列末尾。
 * 以此类推，直到所有元素均排序完毕。
 *
 * @author enhao
 */
public class SelectionSort<T extends Comparable<T>> implements Sort<T> {

    @Override
    public void sort(T[] values) {
        for (int i = 0; i < values.length; i++) {
            // 每趟排序，min用于存放最小元素下标
            int min = i;
            for (int j = i + 1; j < values.length; j++) {
                if (values[j].compareTo(values[min]) < 0) {
                    min = j;
                }
            }

            // min 发生变化，交换
            if (min != i) {
                T temp = values[i];
                values[i] = values[min];
                values[min] = temp;
            }
        }
    }
}
