package com.zc.base.core.collections.tree.array;

import com.zc.base.core.collections.tree.Tree;
import com.zc.base.core.collections.tree.TreeNode;
import com.zc.base.core.collections.tree.TreeRoot;

import java.util.*;

public class ArrayTree<T> implements Tree<T> {
    private static final long serialVersionUID = 7161315168915445688L;
    private TreeRoot<T> root;
    private final List<List<TreeNode<T>>> levels = new ArrayList();

    public ArrayTree() {
        this(null);
    }

    public ArrayTree(T element) {
        this.root = new ArrayTreeRoot(this);
        this.root.setElement(element);
        saveNode(this.root, 0);
    }

    private void addLevel(TreeNode<T> node) {
        List<TreeNode<T>> level = new ArrayList();
        level.add(node);
        this.levels.add(level);
    }

    protected void saveNode(TreeNode<T> node, int depth) {
        if (depth >= this.levels.size()) {
            addLevel(node);
        } else
            ((List) this.levels.get(depth)).add(node);
    }

    protected void removeNode(TreeNode<T> node, int depth) {
        if (depth < this.levels.size()) ((List) this.levels.get(depth)).remove(node);
    }

    protected List<TreeNode<T>> getNodes(int depth) {
        if (depth < this.levels.size()) {
            return (List) this.levels.get(depth);
        }

        return null;
    }

    public TreeRoot<T> getRoot() {
        return this.root;
    }

    public Iterator<T> iterator() {
        return getDepthFirstElements().iterator();
    }

    public Iterator<TreeNode<T>> nodeIterator() {
        return getBreadthFirstNodes().iterator();
    }

    public List<T> getBreadthFirstElements() {
        List<TreeNode<T>> nodes = getBreadthFirstNodes();
        List<T> elements = new ArrayList(nodes.size());
        for (TreeNode<T> node : nodes) {
            elements.add(node.getElement());
        }

        return Collections.unmodifiableList(elements);
    }

    public List<TreeNode<T>> getBreadthFirstNodes() {
        List<TreeNode<T>> nodes = new LinkedList();
        for (List<TreeNode<T>> level : this.levels) {
            nodes.addAll(level);
        }
        return Collections.unmodifiableList(nodes);
    }

    public List<T> getDepthFirstElements() {
        List<TreeNode<T>> nodes = getDepthFirstNodes();
        List<T> elements = new ArrayList(nodes.size());
        for (TreeNode<T> node : nodes) {
            elements.add(node.getElement());
        }

        return Collections.unmodifiableList(elements);
    }

    public List<TreeNode<T>> getDepthFirstNodes() {
        List<TreeNode<T>> nodes = new LinkedList();
        addDepthFirst(nodes, this.root);
        return Collections.unmodifiableList(nodes);
    }

    private void addDepthFirst(List<TreeNode<T>> nodes, TreeNode<T> node) {
        nodes.add(node);
        for (TreeNode<T> child : node.getChildren()) {
            addDepthFirst(nodes, child);
        }
    }

    public int depth() {
        return this.levels.size() - 1;
    }

    public int size() {
        int size = 0;
        for (List<TreeNode<T>> level : this.levels) {
            size += level.size();
        }

        return size;
    }

    public List<T> getElements() {
        return getBreadthFirstElements();
    }

    public List<TreeNode<T>> getNodes() {
        return getBreadthFirstNodes();
    }
}
