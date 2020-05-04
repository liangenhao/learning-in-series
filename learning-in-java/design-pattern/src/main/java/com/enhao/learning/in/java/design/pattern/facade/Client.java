package com.enhao.learning.in.java.design.pattern.facade;

/**
 * 门面模式
 * 门面对象是外界访问子系统内部的唯一通道。
 * 门面角色没有实际的业务逻辑，只是一个委托类。
 * 子系统并不知道门面的存在。
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.methodA();
        facade.methodB();
        facade.methodC();
    }
}
