package com.zc.base.sys.modules.user.entity;

import java.util.Date;


public class UserHis {
    private Long id;
    private Long userId;
    private String password;
    private Date modifyTime;

    public UserHis() {
    }

    public UserHis(Long userId, String password, Date modifyTime) {
        this.userId = userId;
        this.password = password;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
