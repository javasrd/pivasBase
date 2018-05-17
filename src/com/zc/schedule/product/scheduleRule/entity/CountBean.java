package com.zc.schedule.product.scheduleRule.entity;

import java.io.Serializable;
/**
 * 
 * 班次次数实体类
 * 
 * @author  Justin
 * @version  v1.0
 */
public class CountBean implements Serializable
{

    private static final long serialVersionUID = 968675233340558639L;
    /**
     * 班次id
     */
    private Integer workId;
    
    /**
     * 班次名称
     */
    private String workName;
    
    /**
     * 每日次数
     */
    private Integer count;

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

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }
    
}
