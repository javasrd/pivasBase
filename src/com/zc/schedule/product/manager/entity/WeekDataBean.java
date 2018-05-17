package com.zc.schedule.product.manager.entity;

import java.io.Serializable;

/**
 * 一周排班每天班次数据实体类
 * 
 * @author  Justin
 * @version  v1.0
 */
public class WeekDataBean implements Serializable
{

    private static final long serialVersionUID = -1359972548030787173L;
    /**
     * 数据id
     */
    private Long id;
    
    /**
     * 人员id
     */
    private Integer user_id;
    
    /**
     *  周几,1,2,3,4,5,6,7分别对应星期
     */
    private String weekType;
    
    /**
     * 班次id
     */
    private Integer workId;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 日期
     */
    private String workDate;
    
    /**
     * 班次名称
     */
    private String workName;
    
    /**
     * 班次类型，默认值0：工作 1:非工作 2::其他 3:正常休息
     */
    private String workType;
    
    /**
     * 总时间小时
     */
    private String totalTime;
    
    /**
     *  班次配色，存放16进制颜色值
     */
    private String workColour;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getWeekType()
    {
        return weekType;
    }

    public void setWeekType(String weekType)
    {
        this.weekType = weekType;
    }

    public Integer getWorkId()
    {
        return workId;
    }

    public void setWorkId(Integer workId)
    {
        this.workId = workId;
    }

    public String getWorkDate()
    {
        return workDate;
    }

    public void setWorkDate(String workDate)
    {
        this.workDate = workDate;
    }

    public String getWorkName()
    {
        return workName;
    }

    public void setWorkName(String workName)
    {
        this.workName = workName;
    }

    public String getWorkType()
    {
        return workType;
    }

    public void setWorkType(String workType)
    {
        this.workType = workType;
    }

    public String getWorkColour()
    {
        return workColour;
    }

    public void setWorkColour(String workColour)
    {
        this.workColour = workColour;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer user_id)
    {
        this.user_id = user_id;
    }

}
