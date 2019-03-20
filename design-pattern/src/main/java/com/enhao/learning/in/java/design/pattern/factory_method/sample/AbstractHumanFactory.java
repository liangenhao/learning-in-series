package com.enhao.learning.in.java.design.pattern.factory_method.sample;

/**
 * 造人的抽象工厂
 *
 * @author enhao
 */
public interface AbstractHumanFactory {

    /**
     * 创建人
     * @param tClass 人种的Class对象
     * @param <T> 必须是Human的实现类
     * @return 人
     */
    <T extends Human> T createHuman(Class<T> tClass);
}
