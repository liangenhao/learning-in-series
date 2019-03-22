package com.enhao.learning.in.java.design.pattern.template_method;

/**
 * @author enhao
 */
/**
 * 具体模板类
 *
 * @author enhao
 */
public class ConcreteClass2 extends AbstractClass {
    @Override
    protected void doSomething() {
        System.out.println("ConcreteClass2 doSomething()");
    }

    @Override
    protected void doAnything() {
        System.out.println("ConcreteClass2 doAnything()");
    }
}
