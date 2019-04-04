package com.enhao.learning.in.java.design.pattern.visitor;

/**
 * 访问者模式
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Element element = ObjectStruture.createElement();
            element.accept(new ConcreteVisitor());
        }

    }
}
