package com.enhao.learning.in.java.design.pattern.composite.lucency_mode;

import java.util.List;

/**
 * 树叶构件
 * 叶子对象，其下再也没有其他分支
 *
 * @author enhao
 */
public class Leaf extends Component {

    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Component> getChildren() {
        throw new UnsupportedOperationException();
    }
}
