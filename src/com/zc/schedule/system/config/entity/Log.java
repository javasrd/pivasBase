package com.zc.schedule.system.config.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * /**
 * 日志类
 *
 * @author jagger
 * @version v1.0
 */
public class Log implements Serializable {
    /**
     * 默认构造函数
     */
    public Log() {

    }

    /**
     * 构造函数
     */
    public Log(Long userAccount, Integer subSystem, String module, String content, Integer status, Date createTime) {
        this.userAccount = userAccount;
        this.subSystem = subSystem;
        this.module = module;
        this.content = content;
        this.status = status;
        if (createTime != null) {
            this.createTime = (Date) createTime.clone();
        } else {
            this.createTime = null;
        }
    }

    private static final long serialVersionUID = -6006712114654780791L;

    /**
     * 日志id
     */
    private Long logId;

    /**
     * 用户
     */
    private Long userAccount;

    /**
     * 子系统
     */
    private Integer subSystem;

    /**
     * 模块
     */
    private String module;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Long userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(Integer subSystem) {
        this.subSystem = subSystem;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        if (createTime != null) {
            return (Date) createTime.clone();
        } else {
            return null;
        }
    }

    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            this.createTime = (Date) createTime.clone();
        } else {
            this.createTime = null;
        }
    }

}
