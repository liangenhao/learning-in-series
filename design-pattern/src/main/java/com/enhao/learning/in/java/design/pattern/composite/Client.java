package com.enhao.learning.in.java.design.pattern.composite;

/**
 * 组合模式
 * 将对象组合成树形结构以表示"部分-整体"的层次结构。
 *
 * 组合模式有两种实现：
 * 安全模式：把树枝节点和树叶节点彻底分开。（本案例）
 * 透明模式：不管树枝节点还是树叶节点都有相同的结构，通过判断getChildren的返回值确定是叶子节点还是树枝节点
 * @author enhao
 */
public class Client {

    public static void main(String[] args) {
        // 根节点
        Composite root = new Composite();
        // 树枝构件
        Composite branch = new Composite();
        // 叶子节点
        Leaf leaf = new Leaf();
        branch.add(leaf);
        root.add(branch);

        // 遍历
        forEach(root);
    }

    private static void forEach(Composite root) {
        for (Component c : root.getChildren()) {
            if (c instanceof Leaf) {
                c.shareMethod();
            } else {
                forEach((Composite) c);
            }
        }
    }
}
