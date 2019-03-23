package com.enhao.learning.in.java.design.pattern.adapter;

/**
 * 适配器模式
 * （也可以说是包装模式）
 * 类型：
 * 1. 类适配器，适配器角色通过继承的方式进行适配。
 * 2. 对象适配器，适配器角色通过聚合关系进行适配，因为Java不支持多继承，如果要同时适配多个源对象，可以使用聚合关系。
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        // 原有的逻辑，直接调用目标角色
        Target target = new ConcreteTarget();
        target.request();

        // 使用适配器模式
        // 将Adaptee 适配成 Target
        Target target1 = new Adapter();
        target1.request();
    }
}
