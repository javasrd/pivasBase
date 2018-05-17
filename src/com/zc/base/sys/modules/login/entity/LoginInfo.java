package com.zc.base.sys.modules.login.entity;

import java.util.Date;


public class LoginInfo {
    private Long loginInfoId;
    private Long userId;
    private String account;
    private Date lstErrLoginTime;
    private Date fstSucLoginTime;
    private Integer errorCount;
    private Integer locked;
    private Date lockTime;
    private Date lockLostEffTime;

    public Long getLoginInfoId() {
        return this.loginInfoId;
    }

    public void setLoginInfoId(Long loginInfoId) {
        this.loginInfoId = loginInfoId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getLstErrLoginTime() {
        return this.lstErrLoginTime;
    }

    public void setLstErrLoginTime(Date lstErrLoginTime) {
        this.lstErrLoginTime = lstErrLoginTime;
    }

    public Date getFstSucLoginTime() {
        return this.fstSucLoginTime;
    }

    public void setFstSucLoginTime(Date fstSucLoginTime) {
        this.fstSucLoginTime = fstSucLoginTime;
    }

    public Integer getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getLocked() {
        return this.locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Date getLockTime() {
        return this.lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getLockLostEffTime() {
        return this.lockLostEffTime;
    }

    public void setLockLostEffTime(Date lockLostEffTime) {
        this.lockLostEffTime = lockLostEffTime;
    }


    public String toString() {
        return


                "LoginInfo [loginInfoId=" + this.loginInfoId + ", userId=" + this.userId + ", account=" + this.account + ", lstErrLoginTime=" + this.lstErrLoginTime + ", fstSucLoginTime=" + this.fstSucLoginTime + ", errorCount=" + this.errorCount + ", locked=" + this.locked + ", lockTime=" + this.lockTime + ", lockLostEffTime=" + this.lockLostEffTime + "]";
    }
}
