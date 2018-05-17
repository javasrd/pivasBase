package com.zc.base.core.collections.tree.array;

import com.zc.base.core.collections.tree.TreeAware;
import com.zc.base.core.collections.tree.TreeNode;
import com.zc.base.core.collections.tree.TreeRoot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayTreeNode<T>
        implements TreeNode<T> {
    private static final long serialVersionUID = 6604828310489397171L;
    private final List<TreeNode<T>> children = new ArrayList();
    private TreeNode<T> parent;
    private T element;

    protected ArrayTreeNode(TreeNode<T> parent) {
        this();
        this.parent = parent;
    }

    protected ArrayTreeNode(TreeNode<T> parent, T element) {
        this(parent);
        setElement(element);
    }


    protected ArrayTreeNode() {
    }


    public TreeNode<T> addElement(T element) {
        TreeNode<T> node = new ArrayTreeNode(this);
        addNode(node);
        node.setElement(element);
        return node;
    }

    public void addNode(TreeNode<T> node) {
        this.children.add(node);
        getTree().saveNode(node, getDepth() + 1);
    }

    public void addNode(TreeRoot<T> root) {
        addAll(root.getChildren());
    }

    public Collection<TreeNode<T>> getChildren() {
        return Collections.unmodifiableCollection(this.children);
    }

    public TreeNode<T> getParent() {
        return this.parent;
    }

    public ArrayTreeRoot<T> getRoot() {
        TreeNode<T> node = this.parent;
        while (!node.isRoot()) {
            node = node.getParent();
        }

        return (ArrayTreeRoot) node;
    }

    public void addAll(Collection<TreeNode<T>> nodes) {
        for (TreeNode<T> node : nodes) {
            addNode(node);
        }
    }

    public T getElement() {
        return (T) this.element;
    }

    public void setElement(T element) {
        this.element = element;

        if ((element != null) && ((element instanceof TreeAware))) {
            ((TreeAware) element).setNode(this);
        }
    }

    public void removeNode(TreeNode<T> node) {
        this.children.remove(node);
        getTree().removeNode(node, getDepth() + 1);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public boolean isRoot() {
        return false;
    }

    public int getDepth() {
        int level = 1;
        TreeNode<T> node = this.parent;
        while (!node.isRoot()) {
            node = node.getParent();
            level++;
        }

        return level;
    }

    public Collection<TreeNode<T>> getSiblings() {
        List<TreeNode<T>> nodes = getTree().getNodes(getDepth());
        Collection<TreeNode<T>> siblings = new ArrayList(nodes.size() - 1);

        for (TreeNode<T> node : nodes) {
            if (node != this) {
                siblings.add(node);
            }
        }
        return siblings;
    }

    public ArrayTree<T> getTree() {
        return getRoot().getTree();
    }
}
