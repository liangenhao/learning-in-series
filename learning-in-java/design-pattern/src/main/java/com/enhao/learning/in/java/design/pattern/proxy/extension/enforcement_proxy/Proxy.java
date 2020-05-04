package com.enhao.learning.in.java.design.pattern.proxy.extension.enforcement_proxy;

/**
 * 代理类
 * @author enhao
 */
public class Proxy implements Subject {

    /**
     * 要代理的类
     */
    private Subject subject = null;

    public Proxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void request() {
        this.before();
        this.subject.request();
        this.after();
    }

    @Override
    public Subject getProxy() {
        return this;
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }
}
