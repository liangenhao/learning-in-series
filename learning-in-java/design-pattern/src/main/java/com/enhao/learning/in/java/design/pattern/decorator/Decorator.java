package com.enhao.learning.in.java.design.pattern.decorator;

/**
 * 抽象装饰者
 * (如果只有一个装饰者，则可以没有抽象装饰者）
 *
 * @author enhao
 */
public abstract class Decorator extends Component {

    private Component component = null;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operate() {
        this.component.operate();
    }
}
