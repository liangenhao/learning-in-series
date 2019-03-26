package com.enhao.learning.in.java.design.pattern.observer;

/**
 * 观察者模式
 *
 * @author enhao
 * @see java.util.Observable
 * @see java.util.Observer
 */
public class Client {
    public static void main(String[] args) {
        // 被观察者
        ConcreteSubject subject = new ConcreteSubject();
        // 观察者
        Observer observer1 = new ConcreteObserver1();
        Observer observer2 = new ConcreteObserver2();

        subject.addObserver(observer1);
        subject.addObserver(observer2);

        subject.eat();

        subject.sleep();
    }
}
