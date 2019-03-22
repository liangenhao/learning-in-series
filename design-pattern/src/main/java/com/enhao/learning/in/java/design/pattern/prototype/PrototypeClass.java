package com.enhao.learning.in.java.design.pattern.prototype;

/**
 * @author enhao
 */
public class PrototypeClass implements Cloneable {

    private String name;

    @Override
    public PrototypeClass clone() {
        PrototypeClass prototypeClass = null;
        try {
            prototypeClass = (PrototypeClass) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return prototypeClass;
    }

}
