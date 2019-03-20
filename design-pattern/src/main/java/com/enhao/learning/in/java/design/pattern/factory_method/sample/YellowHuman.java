package com.enhao.learning.in.java.design.pattern.factory_method.sample;

/**
 * 黄色人种
 *
 * @author enhao
 */
public class YellowHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黄色人种的皮肤是黄色的。");
    }

    @Override
    public void talk() {
        System.out.println("黄色人种说话。");
    }
}
