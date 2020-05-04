package com.enhao.learning.in.java.design.pattern.memento.memento_with_clone;

/**
 * clone方式的备忘录
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setState("初始状态");
        System.out.println(originator.getState());
        // 创建备份
        originator.createMemento();
        originator.setState("啊啊啊啊啊");
        System.out.println(originator.getState());
        // 恢复状态
        originator.restoreMemento();
        System.out.println(originator.getState());

    }
}
