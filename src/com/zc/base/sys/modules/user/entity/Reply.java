package com.zc.base.sys.modules.user.entity;


public class Reply {
    private String account;


    private String ipAddr;


    private String replyCreateTime;


    public Reply(String account, String ipAddr, String replyCreateTime) {
        this.account = account;
        this.ipAddr = ipAddr;
        this.replyCreateTime = replyCreateTime;
    }


    public Reply() {
    }


    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIpAddr() {
        return this.ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getReplyCreateTime() {
        return this.replyCreateTime;
    }

    public void setReplyCreateTime(String replyCreateTime) {
        this.replyCreateTime = replyCreateTime;
    }
}
