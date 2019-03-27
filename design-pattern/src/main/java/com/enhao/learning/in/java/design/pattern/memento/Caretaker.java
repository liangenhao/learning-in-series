package com.enhao.learning.in.java.design.pattern.memento;

/**
 * 备忘录管理员角色
 *
 * @author enhao
 */
public class Caretaker {

    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
