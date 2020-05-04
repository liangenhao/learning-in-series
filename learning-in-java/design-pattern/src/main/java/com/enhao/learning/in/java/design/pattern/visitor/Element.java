package com.enhao.learning.in.java.design.pattern.visitor;

/**
 * 抽象元素
 * 声明接受哪一类访问者访问，通过 accept 方法中的参数来定义。
 * @author enhao
 */
public abstract class Element {

    /**
     * 业务逻辑
     */
    public abstract void doSomething();

    /**
     * 允许谁来访问
     * @param visitor 访问者
     */
    public abstract void accept(IVisitor visitor);
}
