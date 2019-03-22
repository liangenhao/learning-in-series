package com.enhao.learning.in.java.design.pattern.proxy.extension.dynamic_proxy.aop;

/**
 * 前置通知
 * @author enhao
 */
public class BeforeAdvice implements IAdvice {
    @Override
    public void exec() {
        System.out.println("前置通知");
    }
}
