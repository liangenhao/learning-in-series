package com.enhao.learning.in.java.design.pattern.chain_of_responsibility;

/**
 * 责任链模式
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();
        handler1.setNext(handler2);
        handler2.setNext(handler3);

        Response response = handler1.handleMessage(new Request(Level.FIRST_LEVEL));
        System.out.println(response);
        Response response1 = handler1.handleMessage(new Request(Level.THIRD_LEVEL));
        System.out.println(response1);
    }
}
