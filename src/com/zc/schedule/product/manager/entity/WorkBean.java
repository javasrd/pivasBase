package com.zc.schedule.product.manager.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 班次实体类
 * 
 * @author  Justin
 * @version  v1.0
 */
public class WorkBean implements Serializable
{

    private static final long serialVersionUID = -887023189657210078L;
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
     * 班次类型，默认值0：工作1:其他 2:正常休息 3:非工作 
     */
    private String workType;
    
    /**
     * 总时间小时
     */
    private String totalTime;
    
    /**
     * 起止时间
     */
    private String time_interval;
    
    /**
     *  班次配色，存放16进制颜色值
     */
    private String workColour;
    
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
    private Integer creater;
    
    /**
     * 复制workid
     */
    private Integer copyId;
    
    /**
     * 两头班
     */
    private String splitWork;
    
    /**
     * 班次页面显示位置
     */
    private String left;
    
    private String width;

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

    public String getTime_interval()
    {
        return time_interval;
    }

    public void setTime_interval(String time_interval)
    {
        this.time_interval = time_interval;
    }

    public String getWorkColour()
    {
        return workColour;
    }

    public void setWorkColour(String workColour)
    {
        this.workColour = workColour;
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
        if (creationTime != null)
        {
            return (Date)creationTime.clone();
        }
        else
        {
            return null;
        }
        
    }

    public void setCreationTime(Date creationTime)
    {
        if (creationTime != null)
        {
            this.creationTime = (Date)creationTime.clone();
        }
        else
        {
            this.creationTime = null;
        }
    }

    public Integer getCreater()
    {
        return creater;
    }

    public void setCreater(Integer creater)
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

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

    public String getLeft()
    {
        return left;
    }

    public void setLeft(String left)
    {
        this.left = left;
    }

    public String getWidth()
    {
        return width;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public String getSplitWork()
    {
        return splitWork;
    }

    public void setSplitWork(String splitWork)
    {
        this.splitWork = splitWork;
    }
   
    public Object deepClone() throws Exception
    {
        
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(this);

        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        return ois.readObject();
        
    }
    
}
