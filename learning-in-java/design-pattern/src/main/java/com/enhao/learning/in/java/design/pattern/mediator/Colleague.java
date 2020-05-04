package com.enhao.learning.in.java.design.pattern.mediator;

/**
 * 抽象同事类
 *
 * @author enhao
 */
public abstract class Colleague {

    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}
