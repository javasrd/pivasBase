package com.zc.schedule.product.personnelgroup.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zc.schedule.product.personnel.entity.Personnel;

public class GroupInfo implements Serializable
{
    private static final long serialVersionUID = 2590290162041885837L;
    /**
     * 分组id
     */
    private Long groupId;
    
    /**
     * 分组名称
     */
    private String groupName;
    
    /**
     * 创建时间
     */
    private Date creationTime;
    
    /**
     * 创建人
     */
    private Long creater;
    
    /**
     * 类型，0:待分配人员,1:其他
     */
    private Integer type;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 人员id
     */
    private Long userId;
    
    /**
     * 人员名称
     */
    private String userName;
    
    /**
     * 有效时间开始
     */
    private String validityStart;
    
    /**
     * 有效时间结束
     */
    private String validityEnd;
    
    /**
     * 分组人员列表
     */
    private List<Personnel> personnelList;
    
    public String getValidityStart()
    {
        return validityStart;
    }
    
    public void setValidityStart(String validityStart)
    {
        this.validityStart = validityStart;
    }
    
    public String getValidityEnd()
    {
        return validityEnd;
    }
    
    public void setValidityEnd(String validityEnd)
    {
        this.validityEnd = validityEnd;
    }
    
    public Long getUserId()
    {
        return userId;
    }
    
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
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
    
    public Date getCreationTime()
    {
        return creationTime == null ? null : (Date)creationTime.clone();
    }
    
    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime == null ? null : (Date)creationTime.clone();
    }
    
    public Long getCreater()
    {
        return creater;
    }
    
    public void setCreater(Long creater)
    {
        this.creater = creater;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public List<Personnel> getPersonnelList()
    {
        return personnelList;
    }
    
    public void setPersonnelList(List<Personnel> personnelList)
    {
        this.personnelList = personnelList;
    }
}
