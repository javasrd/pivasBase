package com.zc.base.sys.modules.architecture.bo;


public class ArchTypeBo {
    private Integer id;


    private String name;


    private Integer parentId;


    private Long modeId;


    private int level;


    private int maxLevel;


    private boolean hasChild;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Long getModeId() {
        return this.modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }


    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }


    public boolean getHasChild() {
        return this.hasChild;
    }
}
