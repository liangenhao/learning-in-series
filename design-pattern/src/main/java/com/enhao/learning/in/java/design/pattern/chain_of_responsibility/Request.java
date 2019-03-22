package com.enhao.learning.in.java.design.pattern.chain_of_responsibility;

/**
 * @author enhao
 */
public class Request {

    private Level requestLevel;

    public Request(Level requestLevel) {
        this.requestLevel = requestLevel;
    }

    public Level getRequestLevel() {
        return requestLevel;
    }

    public void setRequestLevel(Level requestLevel) {
        this.requestLevel = requestLevel;
    }
}
