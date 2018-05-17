package com.zc.base.sys.modules.user.entity;

import java.io.Serializable;


public class Privilege
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long privilegeId;
    private Long parentId;
    private String privilegeCode;
    private String name;
    private Integer type;
    private String url;
    private Integer seq;
    private Integer isHttps;
    private Integer level;
    private Integer sysCode;
    private boolean isUse;
    private boolean isCheck;
    private String icon;

    public boolean isUse() {
        return this.isUse;
    }

    public void setUse(boolean isUse1) {
        this.isUse = isUse1;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean isCheck1) {
        this.isCheck = isCheck1;
    }

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

    public Integer getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(Integer sysCode) {
        this.sysCode = sysCode;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPrivilegeCode() {
        return this.privilegeCode;
    }

    public void setPrivilegeCode(String privilegeCode) {
        this.privilegeCode = privilegeCode;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }
}
