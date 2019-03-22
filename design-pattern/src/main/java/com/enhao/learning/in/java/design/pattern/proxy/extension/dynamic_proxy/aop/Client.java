package com.enhao.learning.in.java.design.pattern.proxy.extension.dynamic_proxy.aop;

import java.lang.reflect.InvocationHandler;

/**
 * AOP
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Subject subject = new RealSubject();
        InvocationHandler handler = new MyInvocationHandler(subject);
        Subject proxy = DynamicProxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), handler);
        proxy.request();
    }
}
