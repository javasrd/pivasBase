package com.zc.schedule.product.workCount.entity;

import java.io.Serializable;

public class SignBean implements Serializable
{

    private static final long serialVersionUID = -2178218041535976909L;
    private String workDate;
    
    private String workId;
    
    private String workName;
    
    private String user_id;
    
    private String user_name;
    
    private String time_interval;
    
    private String totaltime;
    
    private String isSign;
    
    private String account;
    /**
     * @return 返回 workDate
     */
    public String getWorkDate()
    {
        return workDate;
    }

    /**
     * @param 对workDate进行赋值
     */
    public void setWorkDate(String workDate)
    {
        this.workDate = workDate;
    }

    /**
     * @return 返回 workId
     */
    public String getWorkId()
    {
        return workId;
    }

    /**
     * @param 对workId进行赋值
     */
    public void setWorkId(String workId)
    {
        this.workId = workId;
    }

    /**
     * @return 返回 workName
     */
    public String getWorkName()
    {
        return workName;
    }

    /**
     * @param 对workName进行赋值
     */
    public void setWorkName(String workName)
    {
        this.workName = workName;
    }

    /**
     * @return 返回 user_name
     */
    public String getUser_name()
    {
        return user_name;
    }

    /**
     * @param 对user_name进行赋值
     */
    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    /**
     * @return 返回 time_interval
     */
    public String getTime_interval()
    {
        return time_interval;
    }

    /**
     * @param 对time_interval进行赋值
     */
    public void setTime_interval(String time_interval)
    {
        this.time_interval = time_interval;
    }

    /**
     * @return 返回 totaltime
     */
    public String getTotaltime()
    {
        return totaltime;
    }

    /**
     * @param 对totaltime进行赋值
     */
    public void setTotaltime(String totaltime)
    {
        this.totaltime = totaltime;
    }

    /**
     * @return 返回 user_id
     */
    public String getUser_id()
    {
        return user_id;
    }

    /**
     * @param 对user_id进行赋值
     */
    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    /**
     * @return 返回 isSign
     */
    public String getIsSign()
    {
        return isSign;
    }

    /**
     * @param 对isSign进行赋值
     */
    public void setIsSign(String isSign)
    {
        this.isSign = isSign;
    }

    /**
     * @return 返回 account
     */
    public String getAccount()
    {
        return account;
    }

    /**
     * @param 对account进行赋值
     */
    public void setAccount(String account)
    {
        this.account = account;
    }

    
}
