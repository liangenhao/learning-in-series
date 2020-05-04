package com.enhao.learning.in.java.design.pattern.visitor;

/**
 * 具体元素
 *
 * @author enhao
 */
public class ConcreteElement2 extends Element {
    @Override
    public void doSomething() {
        System.out.println("concrete element 2 do something");
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
