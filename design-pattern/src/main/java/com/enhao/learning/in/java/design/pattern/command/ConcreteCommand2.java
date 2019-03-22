package com.enhao.learning.in.java.design.pattern.command;

/**
 * @author enhao
 */
public class ConcreteCommand2 extends Command {

    private Receiver receiver;

    public ConcreteCommand2(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.doA();
        receiver.doB();
        receiver.doC();
    }
}
