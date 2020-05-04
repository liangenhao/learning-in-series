package com.enhao.learning.in.java.design.pattern.memento;

/**
 * 发起人角色
 * 记录当前时刻的内部状态，负责定义哪些属于备份范围的状态
 * 负责创建和恢复备忘录
 *
 * @author enhao
 */
public class Originator {

    /**
     * 内部状态(需要备忘的)
     */
    private String state = "";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * 创建一个备忘录
     *
     * @return 新的beiwangl
     */
    public Memento createMemento() {
        return new Memento(this.state);
    }

    /**
     * 恢复一个备忘录
     *
     * @param memento 需要恢复的备忘录
     */
    public void restoreMemento(Memento memento) {
        this.setState(memento.getState());
    }
}
