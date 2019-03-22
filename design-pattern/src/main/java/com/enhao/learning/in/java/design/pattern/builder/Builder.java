package com.enhao.learning.in.java.design.pattern.builder;

/**
 * @author enhao
 */
public abstract class Builder {
    /**
     * 设置产品的不同部分，以获得不同的产品
     */
    public abstract void setPart();

    /**
     * 建造商品
     * @return
     */
    public abstract Product buildProduct();
}
