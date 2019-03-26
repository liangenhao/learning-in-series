package com.enhao.learning.in.java.algorithm.sort;

/**
 * 排序通用接口
 *
 * @author enhao
 */
public interface Sort<T extends Comparable<T>> {

    /**
     * 排序方法
     *
     * @param values 需要排序的数组
     */
    void sort(T[] values);

    /**
     * 创建一个需要排序的数组
     * @param values 数组元素
     * @param <T> 数组元素类型
     * @return 数组
     */
    static <T> T[] of(T... values) {
        return values;
    }
}
