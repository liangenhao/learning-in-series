package com.enhao.learning.in.java.design.pattern.visitor;

/**
 * 具体元素
 * 实现 accept 方法，通常是 visitor.visit(this)
 *
 * @author enhao
 */
public class ConcreteElement1 extends Element {
    @Override
    public void doSomething() {
        System.out.println("concrete element 1 do something");
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
