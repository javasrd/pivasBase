package com.zc.base.sys.modules.user.entity;


public class ArchitectureRefUser {
    private Long architectureId;


    private Long userId;


    private String acrchName;


    public Long getArchitectureId() {
        return this.architectureId;
    }

    public void setArchitectureId(Long architectureId) {
        this.architectureId = architectureId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAcrchName() {
        return this.acrchName;
    }

    public void setAcrchName(String acrchName) {
        this.acrchName = acrchName;
    }
}
