package com.zc.schedule.product.report.entity;

import java.io.Serializable;
import java.util.List;

public class UserBean implements Serializable
{

    private static final long serialVersionUID = -4979153734520239683L;
    /**
     * 用户id
     */
    private String user_id;
    
    /**
     * 用户名
     */
    private String user_name;
    
    /**
     * 工作时间
     */
    private List<WorkBean> workTimeList;
    
    /**
     * 总时间
     */
    private String totaltime;

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public List<WorkBean> getWorkTimeList()
    {
        return workTimeList;
    }

    public void setWorkTimeList(List<WorkBean> workTimeList)
    {
        this.workTimeList = workTimeList;
    }

    public String getTotaltime()
    {
        return totaltime;
    }

    public void setTotaltime(String totaltime)
    {
        this.totaltime = totaltime;
    }
    
}
