package com.enhao.learning.in.java.design.pattern.template_method;

/**
 * 模板方法模式
 *
 * 模版方法模式案例：AbstractQueuedSynchronizer
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        AbstractClass class1 = new ConcreteClass1();
        AbstractClass class2 = new ConcreteClass2();

        class1.templateMethod();
        class2.templateMethod();


    }
}
