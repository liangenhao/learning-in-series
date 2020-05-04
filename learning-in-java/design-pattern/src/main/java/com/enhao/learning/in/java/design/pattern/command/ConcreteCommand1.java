package com.enhao.learning.in.java.design.pattern.command;

import java.beans.FeatureDescriptor;

/**
 * @author enhao
 */
public class ConcreteCommand1 extends Command {

    public ConcreteCommand1() {
        super(new ConcreteReceiver1());
    }

    @Override
    public void execute() {
        receiver.doA();
        receiver.doB();
        receiver.doC();
    }
}
