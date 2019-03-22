package com.enhao.learning.in.java.design.pattern.template_method;

/**
 * 具体模板类
 *
 * @author enhao
 */
public class ConcreteClass1 extends AbstractClass {
    @Override
    protected void doSomething() {
        System.out.println("ConcreteClass1 doSomething()");
    }

    @Override
    protected void doAnything() {
        System.out.println("ConcreteClass1 doAnything()");
    }
}
