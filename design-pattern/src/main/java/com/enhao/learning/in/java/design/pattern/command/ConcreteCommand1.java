package com.enhao.learning.in.java.design.pattern.command;

import java.beans.FeatureDescriptor;

/**
 * @author enhao
 */
public class ConcreteCommand1 extends Command {

    private Receiver receiver;

    public ConcreteCommand1(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.doA();
        receiver.doB();
        receiver.doC();
    }
}
