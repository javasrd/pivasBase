package com.zc.schedule.product.manager.entity;

import java.io.Serializable;
import java.util.List;

public class ManagerBean implements Serializable
{

    private static final long serialVersionUID = 1911259518192864211L;

    private Long id;

    private Long groupId;
    
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
     * 岗位id
     */
    private Integer postId;
    
    /**
     * 岗位名称
     */
    private String postName;
    
    /**
     * 本周欠休
     */
    private String week_owetime;
    
    /**
     * 累计欠休
     */
    private String total_owetime;
    
    /**
     * 总共时
     */
    private String total_time;
    
    /**
     * 起始日期
     */
    private String startDate;
    
    /**
     * 结束日期
     */
    private String endDate;
    
    /**
     * 有效时间开始
     */
    private String validity_start;
    
    /**
     * 有效时间结束
     */
    private String validity_end;
    
    /**
     * 累计欠休是否更改
     */
    private Float changeTotalOwnTime;
    
    /**
     * 一周排班数据集合
     * 
     */
    private List<WeekBean> weekBeanList;
    
    
    /**
     * 一周排班每天班次数据
     */
    private List<WeekDataBean>  weekDataBeanList;
     
    
    /**
     * 上周一周排班数据str
     */
    
    private List<TempBean> lastWeekList;
    
    /**
     * 日工作排班数据 
     */
    private List<WorkBean> dayWorkList;
    

    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
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


    public Integer getPostId()
    {
        return postId;
    }


    public void setPostId(Integer postId)
    {
        this.postId = postId;
    }


    public String getPostName()
    {
        return postName;
    }


    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getStartDate()
    {
        return startDate;
    }


    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }


    public String getEndDate()
    {
        return endDate;
    }


    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }


    public String getValidity_start()
    {
        return validity_start;
    }


    public void setValidity_start(String validity_start)
    {
        this.validity_start = validity_start;
    }


    public String getValidity_end()
    {
        return validity_end;
    }


    public void setValidity_end(String validity_end)
    {
        this.validity_end = validity_end;
    }

    public List<WeekBean> getWeekBeanList()
    {
        return weekBeanList;
    }


    public void setWeekBeanList(List<WeekBean> weekBeanList)
    {
        this.weekBeanList = weekBeanList;
    }


    public List<WeekDataBean> getWeekDataBeanList()
    {
        return weekDataBeanList;
    }


    public void setWeekDataBeanList(List<WeekDataBean> weekDataBeanList)
    {
        this.weekDataBeanList = weekDataBeanList;
    }


    public List<TempBean> getLastWeekList()
    {
        return lastWeekList;
    }


    public void setLastWeekList(List<TempBean> lastWeekList)
    {
        this.lastWeekList = lastWeekList;
    }

    public Float getChangeTotalOwnTime()
    {
        return changeTotalOwnTime;
    }


    public void setChangeTotalOwnTime(Float changeTotalOwnTime)
    {
        this.changeTotalOwnTime = changeTotalOwnTime;
    }


    public String getWeek_owetime()
    {
        return week_owetime;
    }


    public void setWeek_owetime(String week_owetime)
    {
        this.week_owetime = week_owetime;
    }


    public String getTotal_owetime()
    {
        return total_owetime;
    }


    public void setTotal_owetime(String total_owetime)
    {
        this.total_owetime = total_owetime;
    }


	public String getTotal_time() {
		return total_time;
	}


	public void setTotal_time(String total_time) {
		this.total_time = total_time;
	}


    public List<WorkBean> getDayWorkList()
    {
        return dayWorkList;
    }


    public void setDayWorkList(List<WorkBean> dayWorkList)
    {
        this.dayWorkList = dayWorkList;
    }

}
