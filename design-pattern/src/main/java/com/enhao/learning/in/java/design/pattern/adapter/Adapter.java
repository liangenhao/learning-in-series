package com.enhao.learning.in.java.design.pattern.adapter;

/**
 * 适配器角色
 *
 * @author enhao
 */
public class Adapter extends Adaptee implements Target {

    @Override
    public void request() {
        super.doSomething();
    }
}
