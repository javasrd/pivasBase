package com.zc.pivas.app.bean;

import java.util.Date;

/**
 * APP用户登录状态记录
 *
 * @author kunkka
 * @version 1.0
 */
public class AppUserLoginBean {
    private long ID;
    private long user_id;
    private Date login_date;
    private String ip_addr;
    private long port;

    public long getID() {
        return ID;
    }

    public void setID(long iD) {
        ID = iD;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Date getLogin_date() {
        return login_date;
    }

    public void setLogin_date(Date login_date) {
        this.login_date = login_date;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }
}