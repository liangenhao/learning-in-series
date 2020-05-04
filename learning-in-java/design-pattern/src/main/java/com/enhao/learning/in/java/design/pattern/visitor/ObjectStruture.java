package com.enhao.learning.in.java.design.pattern.visitor;

import java.util.Random;

/**
 * 结构对象
 * 元素产生者
 *
 * @author enhao
 */
public class ObjectStruture {

    /**
     * 创建元素
     * @return 新创建的元素
     */
    public static Element createElement() {
        Random random = new Random();
        if (random.nextInt(100) > 50) {
            return new ConcreteElement1();
        } else {
            return new ConcreteElement2();
        }
    }
}
