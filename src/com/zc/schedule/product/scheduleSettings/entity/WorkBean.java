package com.zc.schedule.product.scheduleSettings.entity;

import java.io.Serializable;
import java.util.Date;


public class WorkBean implements Serializable
{

    private static final long serialVersionUID = -5149052120125324033L;
    /**
     * 班次id
     */
    private Integer workId;
    
    /**
     * 班次名称
     */
    private String workName;
    
    
    /**
     * 定义类型,默认值0：自定义
     */
    private String defineType;
    
    /**
     * 班次类型，默认值0：工作, 1:其他, 2:正常休息, 3:非工作 
     */
    private String workType;
    
    /**
     * 总时间小时
     */
    private String totalTime;
    
    /**
     * 起止时间
     */
    private String timeInterval;
    
    /**
     * 班次配色，存放16进制颜色值
     */
    private String workColor;
    
    /**
     * 班次是否使用，0:否/1:是
     */
    private String workStatus;
    
    /**
     * 班次状态，0:无效/1:有效
     */
    private String status;
    
    /**
     * 每日次数
     */
    private Integer count;
    
    /**
     * 创建时间
     */
    private Date creationTime;
    
    /**
     * 创建人
     */
    private Long creater;
    
    /**
     * 复制workid
     */
    private Integer copyId;
    
    /**
     * 是否是两头班,0:否/1是
     */
    private String splitWork;
    
    /**
     * 旧的班次Id
     */
    private Integer oldWorkId;

    public Integer getWorkId()
    {
        return workId;
    }

    public void setWorkId(Integer workId)
    {
        this.workId = workId;
    }

    public String getWorkName()
    {
        return workName;
    }

    public void setWorkName(String workName)
    {
        this.workName = workName;
    }

    public String getDefineType()
    {
        return defineType;
    }

    public void setDefineType(String defineType)
    {
        this.defineType = defineType;
    }

    public String getWorkType()
    {
        return workType;
    }

    public void setWorkType(String workType)
    {
        this.workType = workType;
    }

    public String getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(String totalTime)
    {
        this.totalTime = totalTime;
    }

    public String getTimeInterval()
    {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval)
    {
        this.timeInterval = timeInterval;
    }

    public String getWorkColor()
    {
        return workColor;
    }

    public void setWorkColor(String workColor)
    {
        this.workColor = workColor;
    }

    public String getWorkStatus()
    {
        return workStatus;
    }

    public void setWorkStatus(String workStatus)
    {
        this.workStatus = workStatus;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Date getCreationTime()
    {
        if (null != creationTime) 
        {
            return (Date)creationTime.clone();
        }
        return null;
    }

    public void setCreationTime(Date creationTime)
    {
        if (null != creationTime) 
        {
            this.creationTime = (Date)creationTime.clone();
        }
        else 
        {
            this.creationTime = null;
        }
    }

    public Long getCreater()
    {
        return creater;
    }

    public void setCreater(Long creater)
    {
        this.creater = creater;
    }

    public Integer getCopyId()
    {
        return copyId;
    }

    public void setCopyId(Integer copyId)
    {
        this.copyId = copyId;
    }

    public Integer getOldWorkId()
    {
        return oldWorkId;
    }

    public void setOldWorkId(Integer oldWorkId)
    {
        this.oldWorkId = oldWorkId;
    }

    public String getSplitWork()
    {
        return splitWork;
    }

    public void setSplitWork(String splitWork)
    {
        this.splitWork = splitWork;
    }

}
