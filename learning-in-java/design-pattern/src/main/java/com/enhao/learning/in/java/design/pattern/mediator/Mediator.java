package com.enhao.learning.in.java.design.pattern.mediator;

/**
 * 抽象中介者
 *
 * @author enhao
 */
public abstract class Mediator {

    protected ConcreteColleague1 c1;

    protected ConcreteColleague2 c2;

    public ConcreteColleague1 getC1() {
        return c1;
    }

    public void setC1(ConcreteColleague1 c1) {
        this.c1 = c1;
    }

    public ConcreteColleague2 getC2() {
        return c2;
    }

    public void setC2(ConcreteColleague2 c2) {
        this.c2 = c2;
    }

    // 中介者模式的业务逻辑
    public abstract void doSomething1();
    public abstract void doSomething2();
}
