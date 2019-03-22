package com.enhao.learning.in.java.design.pattern.mediator;

/**
 * @author enhao
 */
public class ConcreteColleague1 extends Colleague {

    public ConcreteColleague1(Mediator mediator) {
        super(mediator);
    }

    // 自己处理的方法
    public void selfMethod1() {
        System.out.println("自己处理");
    }

    // 依赖其他同事类的方法
    public void depMethod2() {
        super.mediator.doSomething1();
    }
}
