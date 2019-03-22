package com.enhao.learning.in.java.design.pattern.mediator;

/**
 * @author enhao
 */
public class ConcreteColleague2 extends Colleague {

    public ConcreteColleague2(Mediator mediator) {
        super(mediator);
    }

    // 自己处理的方法
    public void selfMethod2() {
        System.out.println("自己处理2");
    }

    public void depMethod2() {
        super.mediator.doSomething2();
    }

}
