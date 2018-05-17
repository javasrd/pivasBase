package com.zc.base.sys.modules.log.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;


public class Log
        implements Serializable {
    private static final long serialVersionUID = -6006712114654780791L;
    private Long id;
    private Long accountId;
    private String account;
    private String[] accounts;
    private String userName;
    private Integer system;
    private String systemName;
    private String module;
    private String moduleName;
    private String content;
    private String[] contents;
    private Integer status;
    private String statusName;
    private Date time;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getSystem() {
        return this.system;
    }

    public void setSystem(Integer system) {
        this.system = system;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getAccounts() {
        return this.accounts;
    }

    public void setAccounts(String[] accounts) {
        this.accounts = accounts;
    }

    public String[] getContents() {
        return this.contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }


    public String toString() {
        return


                "Log [id=" + this.id + ", accountId=" + this.accountId + ", account=" + this.account + ", accounts=" + Arrays.toString(this.accounts) + ", userName=" + this.userName + ", system=" + this.system + ", systemName=" + this.systemName + ", module=" + this.module + ", moduleName=" + this.moduleName + ", content=" + this.content + ", contents=" + Arrays.toString(this.contents) + ", status=" + this.status + ", statusName=" + this.statusName + ", time=" + this.time + "]";
    }
}
