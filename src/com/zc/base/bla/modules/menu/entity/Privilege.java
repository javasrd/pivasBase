package com.zc.base.bla.modules.menu.entity;

import java.io.Serializable;


public class Privilege implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long privilegeId;
    private Long parentId;
    private String name;
    private Integer type;
    private String url;
    private String module;
    private Integer seq;
    private Integer isHttps;
    private Integer isLeaf;
    private String description;

    public Long getPrivilegeId() {
        return this.privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getIsHttps() {
        return this.isHttps;
    }

    public void setIsHttps(Integer isHttps) {
        this.isHttps = isHttps;
    }

    public Integer getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Privilege [privilegeId=");
        builder.append(this.privilegeId);
        builder.append(", parentId=");
        builder.append(this.parentId);
        builder.append(", name=");
        builder.append(this.name);
        builder.append(", type=");
        builder.append(this.type);
        builder.append(", url=");
        builder.append(this.url);
        builder.append(", module=");
        builder.append(this.module);
        builder.append(", seq=");
        builder.append(this.seq);
        builder.append(", isHttps=");
        builder.append(this.isHttps);
        builder.append(", isLeaf=");
        builder.append(this.isLeaf);
        builder.append(", description=");
        builder.append(this.description);
        builder.append("]");
        return builder.toString();
    }
}
