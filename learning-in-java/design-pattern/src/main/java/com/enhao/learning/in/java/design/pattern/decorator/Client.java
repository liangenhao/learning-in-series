package com.enhao.learning.in.java.design.pattern.decorator;

/**
 * 装饰者模式
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Component component = new ConcreteComponent();

        // 第一次装饰
        component = new ConcreteDecorator1(component);
        // 第二次装饰
        component = new ConcreteDecorator2(component);

        component.operate();

    }
}
