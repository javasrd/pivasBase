package com.zc.schedule.product.personnel.entity;

import java.io.Serializable;
import java.util.Date;

public class Personnel implements Serializable
{

    private static final long serialVersionUID = -529604378158276031L;
    /**
     * 人员id
     */
    private Long userId;
    
    /**
     * 人员名称
     */
    private String userName;
    
    /**
     * 参与排班，1参与/0不参与
     */
    private String partake = "0";
    
    /**
     * email
     */
    private String email;
    
    /**
     * 电话
     */
    private String tellphone;
    
    /**
     * 账号状态,1创建/0未创建
     */
    private String accountStatus;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 创建人
     */
    private Long creater;
    
    /**
     * 创建时间
     */
    private Date creationTime;
    
    /**
     * 操作标识
     */
    private String opr;
    
    public String getOpr()
    {
        return opr;
    }
    
    public void setOpr(String opr)
    {
        this.opr = opr;
    }
    
    public Long getUserId()
    {
        return userId;
    }
    
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getPartake()
    {
        return partake;
    }
    
    public void setPartake(String partake)
    {
        if (partake == null || !"1".equals(partake))
        {
            this.partake = "0";
        }
        else
        {
            this.partake = partake;
        }
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getTellphone()
    {
        return tellphone;
    }
    
    public void setTellphone(String tellphone)
    {
        this.tellphone = tellphone;
    }
    
    public String getAccountStatus()
    {
        return accountStatus;
    }
    
    public void setAccountStatus(String accountStatus)
    {
        this.accountStatus = accountStatus;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public Long getCreater()
    {
        return creater;
    }
    
    public void setCreater(Long creater)
    {
        this.creater = creater;
    }
    
    public Date getCreationTime()
    {
        return creationTime == null ? null : (Date)creationTime.clone();
    }
    
    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime == null ? null : (Date)creationTime.clone();
    }
}
