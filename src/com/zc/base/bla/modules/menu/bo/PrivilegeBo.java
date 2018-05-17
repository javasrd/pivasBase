package com.zc.base.bla.modules.menu.bo;

import com.zc.base.bla.modules.menu.entity.Privilege;


public class PrivilegeBo extends Privilege {
    private static final long serialVersionUID = 1L;
    private String id;
    private String text;
    private boolean isLeaf;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}
