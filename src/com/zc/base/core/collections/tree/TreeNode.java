package com.zc.base.core.collections.tree;

import java.io.Serializable;
import java.util.Collection;

public abstract interface TreeNode<T>
        extends Serializable {
    public abstract TreeRoot<T> getRoot();

    public abstract TreeNode<T> getParent();

    public abstract void addNode(TreeNode<T> paramTreeNode);

    public abstract void addNode(TreeRoot<T> paramTreeRoot);

    public abstract TreeNode<T> addElement(T paramT);

    public abstract void removeNode(TreeNode<T> paramTreeNode);

    public abstract void setElement(T paramT);

    public abstract T getElement();

    public abstract void addAll(Collection<TreeNode<T>> paramCollection);

    public abstract Collection<TreeNode<T>> getChildren();

    public abstract Collection<TreeNode<T>> getSiblings();

    public abstract boolean isLeaf();

    public abstract boolean isRoot();

    public abstract int getDepth();

    public abstract Tree<T> getTree();
}
