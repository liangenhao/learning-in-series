package com.enhao.learning.in.java.design.pattern.proxy.extension.general_proxy;

/**
 * 代理模式扩展：普通代理
 * 客户端只能访问代理角色，而不能访问真实角色.
 * 调用者只知道代理的存在，不知道代理了谁。
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Subject subject = new Proxy("张三");
        subject.request();
    }
}
