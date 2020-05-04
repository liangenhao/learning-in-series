package com.enhao.learning.in.java.jvm.classloader;

/**
 * 关于类加载器测试
 *
 * 为什么我要做这个测试动机：
 * 学习Spring源码时，时常看到调用的一个方法 {@link org.springframework.util.ClassUtils#getDefaultClassLoader}
 * 获取一个默认的类加载器，它的实现是
 * 1. 获取当前线程上下文类加载器
 * 2. 如果失败，通过当前类的Class对象获取类加载器
 * 3. 如果失败，调用 {@link ClassLoader#getSystemClassLoader()} 方法获取类加载器
 * 因此想测试下，他们的区别？
 * 结果：都一样？？？都是AppClassLoader
 *
 * @author enhao
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("线程上下文classLoader：" + contextClassLoader);

        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println("Class对象的classLoader：" + classLoader);

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统classLoader：" + systemClassLoader);


    }
}
