package com.zc.base.core.collections.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract interface Tree<T>
        extends Iterable<T>, Serializable {
    public abstract TreeRoot<T> getRoot();

    public abstract Iterator<T> iterator();

    public abstract Iterator<TreeNode<T>> nodeIterator();

    public abstract Collection<T> getBreadthFirstElements();

    public abstract Collection<TreeNode<T>> getBreadthFirstNodes();

    public abstract Collection<T> getDepthFirstElements();

    public abstract Collection<TreeNode<T>> getDepthFirstNodes();

    public abstract Collection<T> getElements();

    public abstract Collection<TreeNode<T>> getNodes();

    public abstract int size();

    public abstract int depth();
}
