package com.enhao.learning.in.java.algorithm.sort;

/**
 * 插入排序
 * 1. 从第一元素开始，该元素认为已经被排序
 * 2. 取出下一个元素，在已排序的元素序列中从后往前扫描
 * 3. 如果该元素（已排序）大于新元素，将该元素移到下一个位置
 * 4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
 * 5. 将新元素插入到下一位置中
 * 6. 重复步骤2
 *
 * @author enhao
 */
public class InsertionSort<T extends Comparable<T>> implements Sort<T> {
    @Override
    public void sort(T[] values) {
        // i 从1开始，因为 0 已经加入已排序序列
        for (int i = 1; i < values.length; i++) {
            // j = i: 将未排序的第一个元素加入已排序队列
            // values[j].compareTo(values[j - 1]) < 0：从后往前比较，后一个元素比前一个元素小，前移一位
            for (int j = i; j > 0 && values[j].compareTo(values[j - 1]) < 0; j--) {
                T temp = values[j];
                values[j] = values[j - 1];
                values[j - 1] = temp;
            }
        }
    }
}
