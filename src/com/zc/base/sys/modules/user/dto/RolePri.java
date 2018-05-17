package com.zc.base.sys.modules.user.dto;

import java.util.List;


public class RolePri {
    private String roleId;
    private List<Long> pris;
    private List<Long> ignorePris;
    private List<Long> demandGroup;
    private Long userId;
    private List<Long> cannotAddGroup;

    public List<Long> getDemandGroup() {
        return this.demandGroup;
    }

    public void setDemandGroup(List<Long> demandGroup) {
        this.demandGroup = demandGroup;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getCannotAddGroup() {
        return this.cannotAddGroup;
    }

    public void setCannotAddGroup(List<Long> cannotAddGroup) {
        this.cannotAddGroup = cannotAddGroup;
    }

    public List<Long> getIgnorePris() {
        return this.ignorePris;
    }

    public List<Long> getPris() {
        return this.pris;
    }

    public void setIgnorePris(List<Long> ignorePris) {
        this.ignorePris = ignorePris;
    }

    public void setPris(List<Long> pris) {
        this.pris = pris;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
