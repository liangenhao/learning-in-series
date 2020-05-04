package com.enhao.learning.in.java.design.pattern.visitor;

/**
 * 具体访问者
 *
 * @author enhao
 */
public class ConcreteVisitor implements IVisitor {

    @Override
    public void visit(ConcreteElement1 element) {
        element.doSomething();
    }

    @Override
    public void visit(ConcreteElement2 element2) {
        element2.doSomething();
    }
}
