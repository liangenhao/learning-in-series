package com.enhao.learning.in.java.design.pattern.proxy.extension.general_proxy;

/**
 * 真实主题类
 *
 * @author enhao
 */
public class RealSubject implements Subject {

    private String name;

    public RealSubject(Subject subject, String name) {
        if (subject == null) {
            throw new RuntimeException("");
        } else {
            this.name = name;
        }
    }

    @Override
    public void request() {
        System.out.println("realSubject request " + name);
    }
}
