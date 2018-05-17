package com.zc.schedule.product.manager.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 一周数据实体类
 * 
 * @author  Justin
 * @version  v1.0
 */
public class WeekBean implements Serializable
{


    private static final long serialVersionUID = -1090360354625206498L;
    /**
     * 日期
     */
    private String workDate;
    
    /**
     * 一周排班
     */
    private List<WeekDataBean> weekDataBean;
    
    /**
     * 分组id
     */
    private Long groupId;
    
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 人员id
     */
    private Integer user_id;
    /**
     * 人员名称
     */
    private String user_name;
    
    /**
     * 每天工作总工时
     */
    private Float workTotalTime;
    
    public String getWorkDate()
    {
        return workDate;
    }
    public void setWorkDate(String workDate)
    {
        this.workDate = workDate;
    }
    public List<WeekDataBean> getWeekDataBean()
    {
        return weekDataBean;
    }
    public void setWeekDataBean(List<WeekDataBean> weekDataBean)
    {
        this.weekDataBean = weekDataBean;
    }
    public Long getGroupId()
    {
        return groupId;
    }
    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }
    public String getGroupName()
    {
        return groupName;
    }
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    public Integer getUser_id()
    {
        return user_id;
    }
    public void setUser_id(Integer user_id)
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
    public Float getWorkTotalTime()
    {
        return workTotalTime;
    }
    public void setWorkTotalTime(Float workTotalTime)
    {
        this.workTotalTime = workTotalTime;
    }

}
