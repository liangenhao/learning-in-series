package com.enhao.learning.in.java.design.pattern.proxy.extension.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 *
 * @author enhao
 */
public class SubjectInvocationHandler implements InvocationHandler {

    private Subject subject = null;

    public SubjectInvocationHandler(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object invoke = method.invoke(subject, args);
        System.out.println("after");
        return invoke;
    }
}
