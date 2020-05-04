package com.enhao.learning.in.java.design.pattern.chain_of_responsibility;

/**
 * @author enhao
 */
public class ConcreteHandler2 extends Handler {
    @Override
    protected Level getHandlerLevel() {
        return Level.SECOND_LEVEL;
    }

    @Override
    protected Response echo(Request request) {
        Response response = new Response();
        response.setMessage("concrete handler 2 echo");

        return response;
    }
}
