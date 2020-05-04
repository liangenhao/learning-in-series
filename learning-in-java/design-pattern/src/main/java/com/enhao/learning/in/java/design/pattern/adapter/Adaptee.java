package com.enhao.learning.in.java.design.pattern.adapter;

/**
 * 源角色
 * 转成目标接口的源，它是已经存在，运行良好的类或对象，经过适配器角色包装后，会称为一个崭新的角色。
 *
 * @author enhao
 */
public class Adaptee {

    public void doSomething() {
        System.out.println("I'm kind of busy, leave me alone, pls!");
    }
}
