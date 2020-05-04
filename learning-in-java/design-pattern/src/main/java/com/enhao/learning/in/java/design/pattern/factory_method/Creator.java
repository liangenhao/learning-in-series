package com.enhao.learning.in.java.design.pattern.factory_method;

/**
 * 抽象工厂类
 *
 * @author enhao
 */
public abstract class Creator {

    /**
     * 创建一个产品对象
     * @param tClass 需要创建的具体产品的Class对象
     * @param <T> 泛型是 Product的子类
     * @return T
     */
    public abstract <T extends Product> T createProduct(Class<T> tClass);
}
