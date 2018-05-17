package com.zc.base.core.collections.tree.array;

import com.zc.base.core.collections.tree.TreeNode;
import com.zc.base.core.collections.tree.TreeRoot;

public class ArrayTreeRoot<T> extends ArrayTreeNode<T> implements TreeRoot<T> {
    private static final long serialVersionUID = 1461763078075522365L;
    private ArrayTree<T> parentTree;

    protected ArrayTreeRoot(ArrayTree<T> parentTree) {
        this.parentTree = parentTree;
    }

    public boolean isRoot() {
        return true;
    }

    public ArrayTree<T> getTree() {
        return this.parentTree;
    }

    public TreeNode<T> getParent() {
        return null;
    }

    public int getDepth() {
        return 0;
    }
}
