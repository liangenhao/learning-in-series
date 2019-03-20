package com.enhao.learning.in.java.design.pattern.factory_method;

/**
 * 方法工厂模式：
 * 抽象产品类 Product 负责定义产品的共性，实现对事物最抽象的定义；
 * Creator 为抽象创建类，也就是抽象工厂，
 * 具体如何创建产品类由具体的实现工厂 ConcreteCreator 完成。
 *
 * 工厂模式扩展
 * - 缩小为简单工厂模式
 * - 升级为多个工厂类，ConcreteProduct1Creator, ConcreteProduct2Creator
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Creator creator = new ConcreteCreator();
        ConcreteProduct1 product = creator.createProduct(ConcreteProduct1.class);
        product.method1();
        product.method2();
    }
}
