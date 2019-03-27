package com.enhao.learning.in.java.design.pattern.memento;

/**
 * 备忘录模式
 * 在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态
 * <p>
 * 该案例是单状态
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        // 发起人
        Originator originator = new Originator();
        originator.setState("初始状态");
        System.out.println(originator.getState());
        // 备忘录管理员
        Caretaker caretaker = new Caretaker();
        // 创建一个备忘录
        Memento memento = originator.createMemento();
        caretaker.setMemento(memento);

        originator.setState("啊啊啊啊，发疯啦");
        System.out.println(originator.getState());
        // 恢复一个备忘录
        originator.restoreMemento(caretaker.getMemento());
        System.out.println(originator.getState());
    }
}
