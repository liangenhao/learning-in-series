package com.enhao.learning.in.java.design.pattern.decorator;

/**
 * 具体装饰者
 *
 * @author enhao
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    // 具体装饰自己的装饰方法
    private void method2() {
        System.out.println("concrete decorator 2 method2");
    }

    @Override
    public void operate() {
        super.operate();
        this.method2();
    }
}
