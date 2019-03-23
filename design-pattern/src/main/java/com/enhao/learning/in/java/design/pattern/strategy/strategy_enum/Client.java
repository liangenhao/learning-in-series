package com.enhao.learning.in.java.design.pattern.strategy.strategy_enum;

/**
 * 策略枚举
 *
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        int exec = Calculator.ADD.exec(1, 3);
        System.out.println(exec);
    }
}
