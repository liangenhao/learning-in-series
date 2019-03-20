package com.enhao.learning.in.java.design.pattern.factory_method.sample;

/**
 * 造人的工厂
 *
 * @author enhao
 */
public class HumanFactory implements AbstractHumanFactory {
    @Override
    public <T extends Human> T createHuman(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
