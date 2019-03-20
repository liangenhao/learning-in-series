package com.enhao.learning.in.java.design.pattern.factory_method.sample;

/**
 * 工厂方法模式 sample
 * <p>
 * 女娲造人
 *
 * @author enhao
 */
public class NvWaClient {

    public static void main(String[] args) {
        AbstractHumanFactory humanFactory = new HumanFactory();

        YellowHuman yellowHuman = humanFactory.createHuman(YellowHuman.class);
        yellowHuman.getColor();
        yellowHuman.talk();

        WhiteHuman whiteHuman = humanFactory.createHuman(WhiteHuman.class);
        whiteHuman.getColor();
        whiteHuman.talk();

    }
}
