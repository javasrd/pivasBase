package com.zc.schedule.product.scheduleSettings.entity;

import java.io.Serializable;
import java.util.Date;

public class PostBean implements Serializable
{

    private static final long serialVersionUID = 575523885815985594L;
    /**
     * 岗位id
     */
    private Integer postId;
    
    /**
     * 岗位名称
     */
    private String postName;
    
    /**
     * 是否可用，1:是/0:否
     */
    private String enabled;
    
    /**
     * 创建时间
     */
    private Date creationTime;
    
    /**
     * 创建人
     */
    private Long creater;

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

    public String getEnabled()
    {
        return enabled;
    }

    public void setEnabled(String enabled)
    {
        this.enabled = enabled;
    }

    public Date getCreationTime()
    {
        if (null != creationTime) 
        {
            creationTime = (Date)creationTime.clone();
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

}
