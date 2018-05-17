package com.zc.schedule.product.scheduleRule.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * 分组规则实体类
 * 
 * @author  Justin
 * @version  v1.0
 */
public class GroupBean implements Serializable
{
    private static final long serialVersionUID = 4238198487364131312L;
    /**
     * 分组Id
     */
    private Integer groupId;
    
    /**
     * 分组名称
     */
    private String groupName;
    
    /**
     * 班次Id
     */
    private Integer workId;
    
    /**
     * 班次名称
     */
    private String workName;
    
    /**
     * 班次列表
     */
    private List<GroupBean> workList;
    
    /**
     * 用户
     */
    private Integer isUse;
    
    /**
     * 班次id
     */
    private String workIds;

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
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

    public List<GroupBean> getWorkList()
    {
        return workList;
    }

    public void setWorkList(List<GroupBean> workList)
    {
        this.workList = workList;
    }

    public Integer getIsUse()
    {
        return isUse;
    }

    public void setIsUse(Integer isUse)
    {
        this.isUse = isUse;
    }

    public String getWorkIds()
    {
        return workIds;
    }

    public void setWorkIds(String workIds)
    {
        this.workIds = workIds;
    }

}
