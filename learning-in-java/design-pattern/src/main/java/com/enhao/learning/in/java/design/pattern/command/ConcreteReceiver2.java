package com.enhao.learning.in.java.design.pattern.command;

/**
 * @author enhao
 */
public class ConcreteReceiver2 extends Receiver {
    @Override
    public void doA() {
        System.out.println("concrete receiver 2 doA");
    }

    @Override
    public void doB() {
        System.out.println("concrete receiver 2 doB");

    }

    @Override
    public void doC() {
        System.out.println("concrete receiver 2 doC");

    }
}
