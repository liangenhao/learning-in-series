package com.enhao.learning.in.java.design.pattern.proxy;

/**
 * 代理模式
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Subject subject = new RealSubject();
        Subject proxy = new Proxy(subject);
        proxy.request();

    }
}
