package com.enhao.learning.in.java.design.pattern.builder;

/**
 * @author enhao
 */
public class Director {
    private Builder builder = new ConcreteProduct();

    public Product getAProduct() {
        builder.setPart();
        return builder.buildProduct();
    }
}
