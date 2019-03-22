package com.enhao.learning.in.java.design.pattern.proxy.extension.enforcement_proxy;

/**
 * 真实主题类
 *
 * @author enhao
 */
public class RealSubject implements Subject {

    private Subject proxy = null;

    @Override
    public void request() {
        if (this.isProxy()) {
            System.out.println("realSubject request");
        } else {
            System.out.println("请使用代理访问");
        }
    }

    @Override
    public Subject getProxy() {
        this.proxy = new Proxy(this);
        return this.proxy;
    }

    /**
     * 检查是否是代理模式访问
     * @return
     */
    private boolean isProxy() {
        if (this.proxy == null) {
            return false;
        }
        return true;
    }
}
