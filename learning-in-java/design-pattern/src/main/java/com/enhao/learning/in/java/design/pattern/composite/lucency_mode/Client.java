package com.enhao.learning.in.java.design.pattern.composite.lucency_mode;

/**
 * 透明模式的组合模式
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        Component root = new Composite();
        Component branch = new Composite();
        Component leaf = new Leaf();
        branch.add(leaf);
        root.add(branch);

        forEach(root);
    }

    private static void forEach(Component root) {
        // 这个模式下，不需要进行强制类型转化，遵循了依赖倒置原则。
        for (Component c : root.getChildren()) {
            if (c instanceof Leaf) {
                c.shareMethod();
            } else {
                forEach(c);
            }
        }
    }
}
