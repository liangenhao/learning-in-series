package com.enhao.learning.in.java.algorithm.bloom_filters;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author enhao
 */
public class SimpleBloomFiltersTest {

    @Test
    public void test() {
        // 一百万数据
        // test(1000000); // 出现了误判

        // 一千万数据
        test(10000000);


    }

    private void test(int size) {
        long star = System.currentTimeMillis();

        SimpleBloomFilters bloomFilters = new SimpleBloomFilters(size);
        for (int i = 0; i < size; i++) {
            bloomFilters.add(i + "");
        }

        Assert.assertTrue(bloomFilters.check(1 + ""));
        Assert.assertTrue(bloomFilters.check(2 + ""));
        Assert.assertTrue(bloomFilters.check(3 + ""));
        Assert.assertTrue(bloomFilters.check(999999 + ""));
        Assert.assertFalse(bloomFilters.check(400230340 + ""));

        long end = System.currentTimeMillis();
        System.out.println(size + "数据，执行时间：" + (end - star) + "ms");
    }
}
