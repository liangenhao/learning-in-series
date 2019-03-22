package com.enhao.learning.in.java.design.pattern.chain_of_responsibility;

/**
 * 责任链模式
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Handler handler = Invoker.getHandler();

        Response response = handler.handleMessage(new Request(Level.FIRST_LEVEL));
        System.out.println(response);
        Response response1 = handler.handleMessage(new Request(Level.THIRD_LEVEL));
        System.out.println(response1);
    }
}
