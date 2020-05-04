package com.enhao.learning.in.java.design.pattern.command;

/**
 * @author enhao
 */
public class ConcreteCommand2 extends Command {

    public ConcreteCommand2() {
        super(new ConcreteReceiver2());
    }

    @Override
    public void execute() {
        receiver.doA();
        receiver.doB();
        receiver.doC();
    }
}
