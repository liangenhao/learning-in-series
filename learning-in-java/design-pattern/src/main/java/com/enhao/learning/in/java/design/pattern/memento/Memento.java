package com.enhao.learning.in.java.design.pattern.memento;

/**
 * 备忘录角色
 *
 * @author enhao
 */
public class Memento {

    private String state = "";

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
