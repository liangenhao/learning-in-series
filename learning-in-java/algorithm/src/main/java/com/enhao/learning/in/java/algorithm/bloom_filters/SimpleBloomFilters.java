package com.enhao.learning.in.java.algorithm.bloom_filters;

/**
 * 简陋版的布隆过滤器
 * 应用场景：
 * - 判断一个元素是否在一个集合中。
 * <p>
 * 优点：
 * - 占用很小的内存空间。
 * - 高效的查询效率。
 * <p>
 * 原理：
 * - 有一个二进制数组，长度为L，所有元素的初始值都为0。
 * - 当写入数据时，对写入的数据做N次hash运算，定位到数组中的位置，并将对应位置上的值改为1。
 * - 查询时，也是以同样的方式定位到数组中，一旦发现其中一位是0，认为数据肯定不存在，否则有可能存在。
 * <p>
 * 特点：
 * - 不存在是肯定不存在。
 * - 存在是可能存在。
 * - 不能清除其中数据。
 * 原因：
 * - 在有限的数组中存放大量的数据，再好的hash算法也会发生冲突，所以会有两个完全不同的数最终定位到一样的数组位置。
 * - 删除也是一样，当把B删除时，由于hash冲突的存在，也把A删除了
 *
 * @author enhao
 * @link https://crossoverjie.top/2018/11/26/guava/guava-bloom-filter/
 */
public class SimpleBloomFilters {

    /**
     * 数组长度
     */
    private int size;

    private int[] elements;

    public SimpleBloomFilters(int size) {
        this.size = size;
        elements = new int[size];
    }

    /**
     * 写入数据
     *
     * @param key
     */
    public void add(String key) {
        int first = hashcode_1(key);
        int second = hashcode_2(key);
        int third = hashcode_3(key);
        elements[first % size] = 1;
        elements[second % size] = 1;
        elements[third % size] = 1;
    }

    /**
     * 判断数据是否存在
     *
     * @param key
     * @return
     */
    public boolean check(String key) {
        int first = hashcode_1(key);
        int second = hashcode_2(key);
        int third = hashcode_3(key);
        int firstIndex = elements[first % size];
        if (firstIndex == 0) {
            return false;
        }
        int secondIndex = elements[second % size];
        if (secondIndex == 0) {
            return false;
        }
        int thirdIndex = elements[third % size];
        if (thirdIndex == 0) {
            return false;
        }
        return true;
    }

    /**
     * hash 算法1
     *
     * @param key
     * @return
     */
    private int hashcode_1(String key) {
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); ++i) {
            hash = 33 * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    /**
     * hash 算法2
     *
     * @param data
     * @return
     */
    private int hashcode_2(String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash ^ data.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    /**
     * hash 算法3
     *
     * @param key
     * @return
     */
    private int hashcode_3(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash);
    }
}
