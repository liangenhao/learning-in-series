package com.enhao.learning.in.java.design.pattern.visitor;

/**
 * 抽象访问者
 * 抽象类/接口，声明访问者可以访问哪些元素，即 visit 方法的参数定义哪些对象是可以被访问的
 * @author enhao
 */
public interface IVisitor {

    void visit(ConcreteElement1 element);

    void visit(ConcreteElement2 element2);
}
