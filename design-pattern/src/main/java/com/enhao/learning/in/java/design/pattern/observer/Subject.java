package com.enhao.learning.in.java.design.pattern.observer;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 被观察者
 *
 * @author enhao
 */
public abstract class Subject {
    private List<Observer> obs = new CopyOnWriteArrayList<>();

    /**
     * 增加一个观察者
     * @param observer
     */
    public void addObserver(Observer observer) {
        this.obs.add(observer);
    }

    /**
     * 删除一个观察者
     * @param observer
     */
    public void delObserver(Observer observer) {
        this.obs.remove(observer);
    }

    /**
     * 通知所有观察者
     */
    public void notifyObservers() {
        for (Observer o : obs) {
            o.update();
        }
    }
}
