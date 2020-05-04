package com.enhao.learning.in.java.design.pattern.factory_method;

/**
 * 具体工厂类
 *
 * @author enhao
 */
public class ConcreteCreator extends Creator {
    @Override
    public <T extends Product> T createProduct(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
