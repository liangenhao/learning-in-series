package com.enhao.learning.in.java.design.pattern.strategy;

/**
 * 策略模式
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Strategy strategy = new ConcreteStrategy1();
        Context context = new Context(strategy);
        context.operate();
    }
}
