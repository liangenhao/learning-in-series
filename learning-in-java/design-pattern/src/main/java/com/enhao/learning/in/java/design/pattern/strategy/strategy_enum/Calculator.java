package com.enhao.learning.in.java.design.pattern.strategy.strategy_enum;

/**
 * @author enhao
 */
public enum Calculator {
    ADD("+") {
        @Override
        public int exec(int a, int b) {
            return a + b;
        }
    },
    SUB("-") {
        @Override
        public int exec(int a, int b) {
            return a - b;
        }
    };

    private String value;

    Calculator(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public abstract int exec(int a, int b);
}
