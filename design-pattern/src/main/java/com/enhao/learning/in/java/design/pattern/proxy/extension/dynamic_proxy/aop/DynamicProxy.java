package com.enhao.learning.in.java.design.pattern.proxy.extension.dynamic_proxy.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author enhao
 */
public class DynamicProxy<T> {
    public static <T> T newProxyInstance(ClassLoader loader,
                                         Class<?>[] interfaces,
                                         InvocationHandler h) {
        // 寻找 JoinPoint 连接点，AOP 框架使用元数据定义
        if (true) {
            new BeforeAdvice().exec();
        }
        return (T) Proxy.newProxyInstance(loader, interfaces, h);
    }
}
