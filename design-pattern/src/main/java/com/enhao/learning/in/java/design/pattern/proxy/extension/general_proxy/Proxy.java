package com.enhao.learning.in.java.design.pattern.proxy.extension.general_proxy;

/**
 * 代理类
 * @author enhao
 */
public class Proxy implements Subject {

    /**
     * 要代理的类
     */
    private Subject subject = null;

    public Proxy() {
        this.subject = new Proxy();
    }

    public Proxy(Subject subject) {
        this.subject = subject;
    }

    public Proxy(String name) {
        this.subject = new RealSubject(this, name);
    }

    @Override
    public void request() {
        this.before();
        this.subject.request();
        this.after();
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }
}
