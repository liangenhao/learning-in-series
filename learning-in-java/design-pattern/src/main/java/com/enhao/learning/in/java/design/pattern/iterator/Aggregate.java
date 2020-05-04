package com.enhao.learning.in.java.design.pattern.iterator;

import java.util.Iterator;

/**
 * 抽象容器类
 *
 * @author enhao
 */
public interface Aggregate<E> {

    void add(E element);

    void remove(E element);

    Iterator<E> iterator();
}
