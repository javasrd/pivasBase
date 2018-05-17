package com.zc.schedule.product.report.entity;

import java.io.Serializable;

public class WorkBean implements Serializable
{

    private static final long serialVersionUID = 5310999314048678626L;
    /**
     * 班次id
     */
    private String workId;
    
    /**
     * 班次名称
     */
    private String workName;
    
    /**
     * 总时间
     */
    private String totalTime;

    public String getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(String totalTime)
    {
        this.totalTime = totalTime;
    }

    public String getWorkId()
    {
        return workId;
    }

    public void setWorkId(String workId)
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

}
