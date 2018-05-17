package com.zc.base.sys.modules.user.entity;

import java.util.ArrayList;
import java.util.List;


public class UserGroup {
    private Long groupId;
    private String groupName;
    private String createTime;
    private Long createId;
    private String createName;
    private String description;
    private boolean isUse;
    private boolean isCheck;
    private List<User> user = new ArrayList();


    private Long[] createIds;


    public Long[] getCreateIds() {
        return this.createIds;
    }

    public void setCreateIds(Long[] createIds) {
        this.createIds = createIds;
    }

    public List<User> getUser() {
        return this.user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public boolean isUse() {
        return this.isUse;
    }

    public void setUse(boolean isUse) {
        this.isUse = isUse;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return this.createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
