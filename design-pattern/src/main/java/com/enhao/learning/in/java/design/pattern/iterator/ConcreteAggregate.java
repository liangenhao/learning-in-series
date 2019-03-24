package com.enhao.learning.in.java.design.pattern.iterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author enhao
 */
public class ConcreteAggregate<E> implements Aggregate<E> {

    private ArrayList<E> list = new ArrayList<>();
    @Override
    public void add(E element) {
        this.list.add(element);
    }

    @Override
    public void remove(E element) {
        this.list.remove(element);
    }

    @Override
    public Iterator<E> iterator() {
        return new ConcreteIterator<>(this.list);
    }

}
