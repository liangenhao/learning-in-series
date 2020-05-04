package com.enhao.learning.in.java.design.pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 树枝构件
 * 组合树枝节点和叶子节点形成树形结构
 *
 * @author enhao
 */
public class Composite extends Component {
    // 容器
    private List<Component> subComponents = new ArrayList<>();

    /**
     * 增加子节点
     * @param component
     */
    public void add(Component component) {
        this.subComponents.add(component);
    }

    /**
     * 删除子节点
     * @param component
     */
    public void remove(Component component) {
        this.subComponents.remove(component);
    }

    public List<Component> getChildren() {
        return this.subComponents;
    }
}
