package com.enhao.learning.in.java.design.pattern.command;

/**
 * 调用者
 *
 * @author enhao
 */
public class Invoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action() {
        this.command.execute();
    }
}
