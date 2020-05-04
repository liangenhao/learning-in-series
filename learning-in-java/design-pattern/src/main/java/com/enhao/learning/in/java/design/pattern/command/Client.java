package com.enhao.learning.in.java.design.pattern.command;

/**
 * @author enhao
 */
public class Client {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();

        Command command = new ConcreteCommand1();
        invoker.setCommand(command);
        invoker.action();
    }
}
