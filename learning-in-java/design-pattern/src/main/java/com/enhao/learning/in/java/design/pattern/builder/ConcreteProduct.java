package com.enhao.learning.in.java.design.pattern.builder;

/**
 * @author enhao
 */
public class ConcreteProduct extends Builder {

    private Product product = new Product();

    @Override
    public void setPart() {
        // 产品类内的逻辑处理
    }

    @Override
    public Product buildProduct() {
        return product;
    }
}
