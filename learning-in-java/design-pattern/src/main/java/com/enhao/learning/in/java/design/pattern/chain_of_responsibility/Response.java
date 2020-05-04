package com.enhao.learning.in.java.design.pattern.chain_of_responsibility;

/**
 * @author enhao
 */
public class Response {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                '}';
    }
}
