package com.enhao.learning.in.java.design.pattern.factory_method;

/**
 * 抽象产品类
 *
 * @author enhao
 */
public abstract class Product {

    /**
     * 产品类的公共方法
     */
    public void method1() {
        System.out.println("产品类的公共方法:method1");
    }

    /**
     * 抽象方法
     */
    public abstract void method2();
}
