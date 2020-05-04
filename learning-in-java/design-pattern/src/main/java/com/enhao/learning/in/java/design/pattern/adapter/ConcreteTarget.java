package com.enhao.learning.in.java.design.pattern.adapter;

/**
 * 具体的目标角色
 *
 * @author enhao
 */
public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("if you need any help, pls call me ");
    }
}
