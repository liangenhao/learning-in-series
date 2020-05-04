package com.enhao.learning.in.java.design.pattern.factory_method.sample;

/**
 * 黑种人
 *
 * @author enhao
 */
public class BlackHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黑色人种的皮肤是黑色的。");
    }

    @Override
    public void talk() {
        System.out.println("黑色人种说话。");
    }
}
