package com.zc.schedule.system.user.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员实体类
 *
 * @author jagger
 * @version v1.0
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员id
     */
    Long userId;

    /**
     * 用户名
     */
    String account;

    /**
     * 名称
     */
    String name;

    /**
     * 电话
     */
    String telephone;

    /**
     * 邮箱
     */
    String email;

    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 删除状态
     */
    Integer delFlag;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

}
