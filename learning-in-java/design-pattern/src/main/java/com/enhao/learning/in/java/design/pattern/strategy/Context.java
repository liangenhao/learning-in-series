package com.enhao.learning.in.java.design.pattern.strategy;

/**
 * 封装
 *
 * @author enhao
 */
public class Context {
    private Strategy strategy = null;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void operate() {
        this.strategy.execute();
    }
}
