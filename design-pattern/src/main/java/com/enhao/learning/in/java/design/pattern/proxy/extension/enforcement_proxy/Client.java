package com.enhao.learning.in.java.design.pattern.proxy.extension.enforcement_proxy;

/**
 * 代理模式扩展：强制代理
 * 高层模块new了一个真实角色的对象，返回的却是代理角色。
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        // 无法访问
        Subject subject1 = new RealSubject();
        subject1.request();

        // 无法访问
        Subject subject2 = new RealSubject();
        Subject proxy = new Proxy(subject2);
        proxy.request();

        // 可以访问
        Subject subject3 = new RealSubject();
        Subject proxy1 = subject3.getProxy();
        proxy1.request();


    }
}
