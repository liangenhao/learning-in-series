package com.enhao.learning.in.java.design.pattern.iterator;

import java.util.Iterator;

/**
 * 迭代器模式
 *
 * 案例：ArrayList
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Aggregate<String> aggregate = new ConcreteAggregate<>();
        aggregate.add("A");
        aggregate.add("B");
        aggregate.add("C");

        Iterator<String> iterator = aggregate.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
