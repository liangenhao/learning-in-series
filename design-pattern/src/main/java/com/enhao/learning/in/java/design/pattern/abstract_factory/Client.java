package com.enhao.learning.in.java.design.pattern.abstract_factory;

/**
 * 抽象工厂模式
 * 抽象工厂和工厂方法类似，它主要解决的是产品族的问题（相关的，互相依赖的产品）
 * 打个比方, 如果我有一个轮胎工厂, 我生产的东西都是轮胎, 只是规格不同, 我就可以使用工厂方法; 如果我是一个汽车工厂, 我生产汽车, 它需要轮胎, 车架, 发动机... 那么我就应用使用抽象工厂.
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        AbstractCreator creator1 = new Creator1();
        AbstractCreator creator2 = new Creator2();

        AbstractProductA productA1 = creator1.createProductA();
        productA1.doSomething();
        AbstractProductB productB1 = creator1.createProductB();
        productB1.show();
        AbstractProductA productA2 = creator2.createProductA();
        productA2.doSomething();
        AbstractProductB productB2 = creator2.createProductB();
        productB2.show();


    }
}
