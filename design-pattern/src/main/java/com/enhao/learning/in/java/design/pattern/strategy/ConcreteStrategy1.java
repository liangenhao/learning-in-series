package com.enhao.learning.in.java.design.pattern.strategy;

/**
 * 具体策略
 *
 * @author enhao
 */
public class ConcreteStrategy1 implements Strategy {
    @Override
    public void execute() {
        System.out.println("策略1");
    }
}
