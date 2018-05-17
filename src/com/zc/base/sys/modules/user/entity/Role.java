package com.zc.base.sys.modules.user.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Role
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long roleId;
    private String name;
    private String[] nameList;
    private Date createTime;
    private String description;
    private String[] descList;
    private List<Privilege> privileges;
    private String roleNumber;
    private Long[] createIds;
    private boolean isUse;
    private boolean isCheck;
    private String sortname;
    private String sortorder;

    public String getSortname() {
        return this.sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getSortorder() {
        return this.sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public boolean isUse() {
        return this.isUse;
    }

    public void setUse(boolean isUse1) {
        this.isUse = isUse1;
    }

    public Long[] getCreateIds() {
        return this.createIds;
    }

    public void setCreateIds(Long[] createIds) {
        this.createIds = createIds;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean isCheck1) {
        this.isCheck = isCheck1;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Privilege> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public String getRoleNumber() {
        return this.roleNumber;
    }

    public void setRoleNumber(String roleNumber) {
        this.roleNumber = roleNumber;
    }

    public String[] getNameList() {
        return this.nameList;
    }

    public void setNameList(String[] nameList) {
        this.nameList = nameList;
    }

    public String[] getDescList() {
        return this.descList;
    }

    public void setDescList(String[] descList) {
        this.descList = descList;
    }


    public String toString() {
        return


                "Role [roleId=" + this.roleId + ", name=" + this.name + ", nameList=" + Arrays.toString(this.nameList) + ", createTime=" + this.createTime + ", description=" + this.description + ", descList=" + Arrays.toString(this.descList) + ", privileges=" + this.privileges + ", roleNumber=" + this.roleNumber + ", createIds=" + Arrays.toString(this.createIds) + ", isUse=" + this.isUse + ", isCheck=" + this.isCheck + ", sortname=" + this.sortname + ", sortorder=" + this.sortorder + "]";
    }
}
