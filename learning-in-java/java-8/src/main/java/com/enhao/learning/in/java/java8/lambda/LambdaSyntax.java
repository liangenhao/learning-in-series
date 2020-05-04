package com.enhao.learning.in.java.java8.lambda;

import com.enhao.learning.in.java.java8.common.Apple;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda 表达式的语法
 *
 * @author enhao
 */
public class LambdaSyntax {

    public static void main(String[] args) {
        // 基本语法
        // (parameters) -> expression
        // 或者
        // (parameters) -> {statements;}

        // 函数式接口：只定义一个抽象方法的接口

        // 函数描述符
        // 1. 有入参，有返回值
        Function<String, Integer> function =  (String s) -> s.length();

        // 2. 有入参，返回值是boolean
        Predicate<Apple> predicate = (Apple a) -> a.getWeight() > 150;

        // 3. 有入参，没有返回值
        IntConsumer consumer = (int x) -> System.out.println(x);

        // 4. 无入参，有返回值
        Supplier<Integer> supplier = () -> 42;

        // 5. 无入参，无返回值
        Runnable runnable = () -> System.out.println("hello com!");

    }
}
