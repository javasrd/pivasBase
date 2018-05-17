package com.zc.base.sys.modules.user.dto;


public class UserLoginVO {
    private Long id;


    private Long userId;


    private String ipAddr;


    private String loginDate;


    public UserLoginVO() {
    }


    public UserLoginVO(Long id, Long userId, String ipAddr, String loginDate) {
        this.id = id;
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

    public String getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }
}
