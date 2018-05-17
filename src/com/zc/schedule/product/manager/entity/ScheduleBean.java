package com.zc.schedule.product.manager.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 排班数据实体类
 * 
 * @author  Justin
 * @version  v1.0
 */
public class ScheduleBean implements Serializable
{

    private static final long serialVersionUID = -1256010825969060248L;
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
     * 有效时间开始
     */
    private String validity_start;
    /**
     * 有效时间结束
     */
    private String validity_end;
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 排班人员
     */
    private List<GroupInfoBean> groupInfoList;

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

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public List<GroupInfoBean> getGroupInfoList()
    {
        return groupInfoList;
    }

    public void setGroupInfoList(List<GroupInfoBean> groupInfoList)
    {
        this.groupInfoList = groupInfoList;
    }
    

}
