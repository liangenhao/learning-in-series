package com.enhao.learning.in.java.design.pattern.proxy.extension.enforcement_proxy;

/**
 * 抽象主题类
 *
 * @author enhao
 */
public interface Subject {

    public void request();

    public Subject getProxy();
}
