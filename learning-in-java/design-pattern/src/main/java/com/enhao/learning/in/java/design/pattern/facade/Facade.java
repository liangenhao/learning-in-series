package com.enhao.learning.in.java.design.pattern.facade;

/**
 * @author enhao
 */
public class Facade {

    // 被委托的对象
    private SubsystemA a = new SubsystemA();
    private SubsystemB b = new SubsystemB();
    private SubsystemC c = new SubsystemC();

    public void methodA() {
        a.doSomethingA();
    }

    public void methodB() {
        b.doSomethingB();
    }

    public void methodC() {
        c.doSomethingC();
    }
}
