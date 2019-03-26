package com.enhao.learning.in.java.design.pattern.composite;

/**
 * 抽象构件
 * 定义参与组合对象的共有方法和属性，也可以定义一些默认的行为或属性
 *
 * @author enhao
 */
public abstract class Component {

    /**
     * 共享方法
     */
    public void shareMethod() {
        System.out.println("share method");
    }
}
