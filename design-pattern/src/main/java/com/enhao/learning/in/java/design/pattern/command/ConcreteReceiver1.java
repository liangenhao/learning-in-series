package com.enhao.learning.in.java.design.pattern.command;

/**
 * @author enhao
 */
public class ConcreteReceiver1 extends Receiver {
    @Override
    public void doA() {
        System.out.println("concrete receiver 1 doA");
    }

    @Override
    public void doB() {
        System.out.println("concrete receiver 1 doB");
    }

    @Override
    public void doC() {
        System.out.println("concrete receiver 1 doC");
    }
}
