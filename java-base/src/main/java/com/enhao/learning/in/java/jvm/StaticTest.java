package com.enhao.test.in.java.jvm;

/**
 * 考察执行顺序
 * <p>
 * 知识点：（详见 《深入理解Java虚拟机》P219）
 * 1. 类加载过程，只有准备阶段(零值初始化)和初始化阶段才会设计类变量的初始化和赋值。
 * 2. 需要注意的是，零值初始化仅包括类变量（static修饰的变量）
 * 3. 有个特殊情况，就是被final修饰的类变量，在准备阶段就会被初始化。
 * 4. 初始化阶段是执行类构造器的过程，类构造器包括：类变量的赋值动作和静态代码块
 * <p>
 * 执行顺序解析：
 * 1. 类准备阶段，对类变量做零值初始化 -> st = null, b = 0
 * 2. 类初始化阶段，包括类变量赋值和静态代码块，按照代码的顺序执行，
 *    因此，首先执行的是 st = new StaticTest()，此时进行对象初始化，对象初始化，先初始化成员变量，再执行构造函数 -> 打印2，打印3，a赋值110，b仍然是0。
 *    然后，按照顺序执行静态代码块 -> 打印1，静态变量b赋值 -> b = 112
 * 3. 执行main方法，调用静态方法staticFunction -> 打印4
 * <p>
 * 这里出现了一个有趣的现象，示例初始化出现在静态初始化之前
 * <p>
 * 所以执行结果：
 * 2
 * 3
 * a=110,b=0
 * 1
 * 4
 *
 * @author enhao
 */
public class StaticTest {
    public static void main(String[] args) {
        staticFunction();
    }

    static StaticTest st = new StaticTest();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    StaticTest() {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void staticFunction() {
        System.out.println("4");
    }

    int a = 110;
    static int b = 112;
}