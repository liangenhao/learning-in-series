package com.enhao.learning.in.java.design.pattern.template_method;

/**
 * 抽象模版类
 *
 * @author enhao
 */
public abstract class AbstractClass {

    /**
     * 基本方法
     */
    protected abstract void doSomething();

    /**
     * 基本方法
     */
    protected abstract void doAnything();

    public void templateMethod() {
        // 调用基本方法
        this.doAnything();
        this.doSomething();
    }
}
