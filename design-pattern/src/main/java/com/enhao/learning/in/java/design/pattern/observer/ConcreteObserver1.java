package com.enhao.learning.in.java.design.pattern.observer;

/**
 * 具体观察者
 *
 * @author enhao
 */
public class ConcreteObserver1 implements Observer {
    @Override
    public void update() {
        System.out.println("concrete observer 1 观察到concrete subject 有活动了");
    }
}
