package com.enhao.learning.in.java.design.pattern.factory_method.sample;

/**
 * 白色人种
 *
 * @author enhao
 */
public class WhiteHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("白色人种的皮肤是白色的。");
    }

    @Override
    public void talk() {
        System.out.println("白色人种说话。");
    }
}
