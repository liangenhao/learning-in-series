package com.enhao.learning.in.java.design.pattern.proxy.extension.dynamic_proxy;

/**
 * 真实主题类
 *
 * @author enhao
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("realSubject request");
    }
}
