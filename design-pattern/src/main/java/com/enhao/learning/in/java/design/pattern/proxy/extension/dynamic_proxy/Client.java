package com.enhao.learning.in.java.design.pattern.proxy.extension.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 动态代理：
 * 注意：
 * 如果遇到 com.sun.proxy.$Proxy0 cannot be cast to java.sql.Connection ... 错误
 * subject.getClass().getInterfaces()/RealSubject.class.getInterfaces() 和 Subject.class.getInterfaces() 的结果是不同的
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Subject subject = new RealSubject();
        InvocationHandler handler = new SubjectInvocationHandler(subject);
        ClassLoader classLoader = subject.getClass().getClassLoader();
        Subject proxyInstance = (Subject) Proxy.newProxyInstance(classLoader, subject.getClass().getInterfaces(), handler);
        proxyInstance.request();

        System.out.println("============test============");
        System.out.println(Subject.class.getClassLoader() == subject.getClass().getClassLoader());
        System.out.println(Arrays.toString(subject.getClass().getInterfaces()));
        System.out.println(Arrays.toString(RealSubject.class.getInterfaces()));
        System.out.println(Arrays.toString(Subject.class.getInterfaces()));
    }
}
