package com.enhao.learning.in.java.design.pattern.decorator;

/**
 * 具体构件
 *
 * @author enhao
 */
public class ConcreteComponent extends Component {
    @Override
    public void operate() {
        System.out.println("operate");
    }
}
