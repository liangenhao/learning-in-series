package com.enhao.learning.in.java.design.pattern.chain_of_responsibility;

/**
 * @author enhao
 */
public class ConcreteHandler1 extends Handler {
    @Override
    protected Level getHandlerLevel() {
        return Level.FIRST_LEVEL;
    }

    @Override
    protected Response echo(Request request) {
        Response response = new Response();
        response.setMessage("concrete handler 1 echo");
        return response;
    }
}
