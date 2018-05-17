package com.zc.pivas.docteradvice.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 医嘱批次修改日志记录
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class OprLog implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 日志类型
     */
    String logType;

    /**
     * 日志时间
     */
    Date oprTime;

    /**
     * 用户ID
     */
    Long userId;

    /**
     * 用户名
     */
    String userName;

    /**
     * 表关联id
     */
    Long tabId;

    /**
     * 表关联id2
     */
    String tabId2;

    /**
     * 原值
     */
    String oldValue;

    /**
     * 修改后值
     */
    String newValue;
    
    public OprLog()
    {
    }
    
    public void initYZLog(Long userId, String userName, String tabId2, String oldValue, String newValue)
    {
        logType = "yzcheck";
        this.userId = userId;
        this.userName = userName;
        this.tabId2 = tabId2;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
    public void initYDLog(Long userId, String userName, String tabId2, String oldValue, String newValue)
    {
        logType = "pcchange";
        this.userId = userId;
        this.userName = userName;
        this.tabId2 = tabId2;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
    public String getLogType()
    {
        return logType;
    }
    
    public void setLogType(String logType)
    {
        this.logType = logType;
    }
    
    public Date getOprTime()
    {
        Date date = new Date(oprTime.getTime());
        return date;
    }
    
    public void setOprTime(Date oprTime)
    {
        Date date = new Date(oprTime.getTime());
        this.oprTime = date;
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
    
    public Long getTabId()
    {
        return tabId;
    }
    
    public void setTabId(Long tabId)
    {
        this.tabId = tabId;
    }
    
    public String getTabId2()
    {
        return tabId2;
    }
    
    public void setTabId2(String tabId2)
    {
        this.tabId2 = tabId2;
    }
    
    public String getOldValue()
    {
        return oldValue;
    }
    
    public void setOldValue(String oldValue)
    {
        this.oldValue = oldValue;
    }
    
    public String getNewValue()
    {
        return newValue;
    }
    
    public void setNewValue(String newValue)
    {
        this.newValue = newValue;
    }
    
}
