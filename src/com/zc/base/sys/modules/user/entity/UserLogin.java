package com.zc.base.sys.modules.user.entity;

import java.util.Date;


public class UserLogin {
    private Long id;
    private Long userId;
    private String ipAddr;
    private Date loginDate;

    public UserLogin() {
    }

    public UserLogin(Long userId, String ipAddr, Date loginDate) {
        this.userId = userId;
        this.ipAddr = ipAddr;
        this.loginDate = loginDate;
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

    public String getIpAddr() {
        return this.ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
