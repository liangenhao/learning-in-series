package com.enhao.learning.in.java.design.pattern.command;

/**
 * 抽象命令类
 *
 * @author enhao
 */
public abstract class Command {

    protected Receiver receiver;

    public Command(Receiver receiver) {
        this.receiver = receiver;
    }

    public abstract void execute();
}
