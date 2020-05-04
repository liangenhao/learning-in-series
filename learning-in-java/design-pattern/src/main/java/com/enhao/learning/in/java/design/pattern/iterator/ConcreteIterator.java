package com.enhao.learning.in.java.design.pattern.iterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author enhao
 */
public class ConcreteIterator<E> implements Iterator<E> {

    private ArrayList<E> list = new ArrayList<>();

    // 当前有表
    private int cursor = 0;

    public ConcreteIterator(ArrayList<E> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return this.cursor != this.list.size();
    }

    @Override
    public E next() {
        E element = null;
        if (this.hasNext()) {
            element = this.list.get(this.cursor++);
        }
        return element;
    }
}
