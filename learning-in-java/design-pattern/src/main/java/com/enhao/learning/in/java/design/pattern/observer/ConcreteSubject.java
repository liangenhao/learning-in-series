package com.enhao.learning.in.java.design.pattern.observer;

/**
 * 具体的被观察者
 *
 * @author enhao
 */
public class ConcreteSubject extends Subject {
    public void eat() {
        System.out.println("concrete subject is eating");
        // 通知观察者
        super.notifyObservers();
    }

    public void sleep() {
        System.out.println("concrete subject is sleeping");
        // 通知观察者
        super.notifyObservers();
    }

    public void play() {
        System.out.println("concrete subject is playing");
        // 通知观察者
        super.notifyObservers();
    }
}
