package com.enhao.learning.in.java.string;

import java.util.StringTokenizer;

/**
 * StringTokenizer 是一个用来分割字符串的工具类
 * 构造参数：
 *  - str：需要分割的字符串
 *  - delim：分割符，默认\t\n\r\f，
 *  - returnDelims： 是否返回分割符，默认false
 *
 * @author enhao
 * @see StringTokenizer
 */
public class StringTokenizerTest {

    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("Hello World");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }
}
