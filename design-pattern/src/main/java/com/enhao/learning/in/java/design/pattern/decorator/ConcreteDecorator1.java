package com.enhao.learning.in.java.design.pattern.decorator;

/**
 * 具体装饰者
 *
 * @author enhao
 */
public class ConcreteDecorator1 extends Decorator {

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    // 具体装饰自己的装饰方法
    private void method1() {
        System.out.println("concrete decorator 1 method1");
    }

    @Override
    public void operate() {
        this.method1();
        super.operate();
    }
}
