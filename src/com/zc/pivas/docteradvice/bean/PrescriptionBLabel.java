package com.zc.pivas.docteradvice.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 药单和瓶签实体类
 * 
 *
 * @author  cacabin
 * @version  1.0
 */
public class PrescriptionBLabel implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 父医嘱编号
     */
    String parentNo;

    /**
     * 执行批次
     */
    Integer zxbc;

    /**
     * 用药日期
     */
    Date yyrq;

    /**
     * 用药日期
     */
    String yyrqS;

    /**
     * 瓶签号
     */
    String ydpq;

    /**
     * 父医嘱+执行时间
     */
    String pidsj;

    /**
     * 父医嘱+执行批次
     */
    String pidrqzxbc;

    /**
     * 频次id
     */
    Long pb_id;

    /**
     * 频次code
     */
    String pb_num;

    /**
     * 频次名称
     */
    String pb_name;

    /**
     * 是否空批
     */
    Integer pb_isEmptyBatch;

    /**
     * 开始时间
     */
    String pb_startTime;

    /**
     * 结束时间
     */
    String pb_endTime;

    /**
     * 是否寄第二日
     */
    Integer pb_isSecendAdvice;

    /**
     * 批次颜色
     */
    String pb_color;

    /**
     * 是否启用
     */
    Integer pb_enabled;

    /**
     * 是否重打
     */
    Integer isRepeat;
    
    public String getParentNo()
    {
        return parentNo;
    }
    
    public void setParentNo(String parentNo)
    {
        this.parentNo = parentNo;
    }
    
    public Integer getZxbc()
    {
        return zxbc;
    }
    
    public void setZxbc(Integer zxbc)
    {
        this.zxbc = zxbc;
    }
    
    public Date getYyrq()
    {
        return yyrq;
    }
    
    public void setYyrq(Date yyrq)
    {
        this.yyrq = yyrq;
    }
    
    public String getYyrqS()
    {
        return yyrqS;
    }
    
    public void setYyrqS(String yyrqS)
    {
        this.yyrqS = yyrqS;
    }
    
    public String getYdpq()
    {
        return ydpq;
    }
    
    public void setYdpq(String ydpq)
    {
        this.ydpq = ydpq;
    }
    
    public String getPidsj()
    {
        return pidsj;
    }
    
    public void setPidsj(String pidsj)
    {
        this.pidsj = pidsj;
    }
    
    public String getPidrqzxbc()
    {
        return pidrqzxbc;
    }
    
    public void setPidrqzxbc(String pidrqzxbc)
    {
        this.pidrqzxbc = pidrqzxbc;
    }
    
    public Long getPb_id()
    {
        return pb_id;
    }
    
    public void setPb_id(Long pb_id)
    {
        this.pb_id = pb_id;
    }
    
    public String getPb_num()
    {
        return pb_num;
    }
    
    public void setPb_num(String pb_num)
    {
        this.pb_num = pb_num;
    }
    
    public String getPb_name()
    {
        return pb_name;
    }
    
    public void setPb_name(String pb_name)
    {
        this.pb_name = pb_name;
    }
    
    public Integer getPb_isEmptyBatch()
    {
        return pb_isEmptyBatch;
    }
    
    public void setPb_isEmptyBatch(Integer pb_isEmptyBatch)
    {
        this.pb_isEmptyBatch = pb_isEmptyBatch;
    }
    
    public String getPb_startTime()
    {
        return pb_startTime;
    }
    
    public void setPb_startTime(String pb_startTime)
    {
        this.pb_startTime = pb_startTime;
    }
    
    public String getPb_endTime()
    {
        return pb_endTime;
    }
    
    public void setPb_endTime(String pb_endTime)
    {
        this.pb_endTime = pb_endTime;
    }
    
    public Integer getPb_isSecendAdvice()
    {
        return pb_isSecendAdvice;
    }
    
    public void setPb_isSecendAdvice(Integer pb_isSecendAdvice)
    {
        this.pb_isSecendAdvice = pb_isSecendAdvice;
    }
    
    public String getPb_color()
    {
        return pb_color;
    }
    
    public void setPb_color(String pb_color)
    {
        this.pb_color = pb_color;
    }
    
    public Integer getPb_enabled()
    {
        return pb_enabled;
    }
    
    public void setPb_enabled(Integer pb_enabled)
    {
        this.pb_enabled = pb_enabled;
    }
    
    public Integer getIsRepeat()
    {
        return isRepeat;
    }
    
    public void setIsRepeat(Integer isRepeat)
    {
        this.isRepeat = isRepeat;
    }
    
}
