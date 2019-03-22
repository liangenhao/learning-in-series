package com.enhao.learning.in.java.design.pattern.mediator;

/**
 * 具体中介类
 *
 * @author enhao
 */
public class ConcreteMediator extends Mediator {
    @Override
    public void doSomething1() {
        // 调用同事类的方法
        super.c1.selfMethod1();
        super.c2.selfMethod2();
    }

    @Override
    public void doSomething2() {
        super.c1.selfMethod1();
        super.c2.selfMethod2();
    }
}
