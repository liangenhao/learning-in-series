package com.enhao.learning.in.java.design.pattern.chain_of_responsibility;

/**
 * 对责任链进行封装
 *
 * @author enhao
 */
public class Invoker {

    public static Handler getHandler() {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();
        handler1.setNext(handler2);
        handler2.setNext(handler3);

        return handler1;
    }
}
