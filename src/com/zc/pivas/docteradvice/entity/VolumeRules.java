package com.zc.pivas.docteradvice.entity;

import com.zc.base.common.util.StrUtil;
import com.zc.base.sys.common.util.NumberUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 
 * 容积规则bean
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class VolumeRules implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public VolumeRules()
    {
        
    }
    
    public VolumeRules(Map<String, Object> map)
    {
        vrId = NumberUtil.getObjLong(map.get("vrId"));
        vrType = NumberUtil.getObjInt(map.get("vrType"));
        deptcode = StrUtil.getObjStr(map.get("deptcode"));
        batchId = NumberUtil.getObjLong(map.get("batchId"));
        overrun = NumberUtil.getObjLong(map.get("overrun"));
        maxval = NumberUtil.getObjLong(map.get("maxval"));
        minval = NumberUtil.getObjLong(map.get("minval"));
        vrUserId = NumberUtil.getObjLong(map.get("vrUserId"));
    }
    
    public static void format(Map<String, Object> map)
    {
        if (map.get("vrId") != null)
        {
            map.put("vrId", NumberUtil.getObjLong(map.get("vrId")));
        }
        if (map.get("batchId") != null)
        {
            map.put("batchId", NumberUtil.getObjLong(map.get("batchId")));
        }
        if (map.get("overrun") != null)
        {
            map.put("overrun", NumberUtil.getObjLong(map.get("overrun")));
        }
        if (map.get("maxval") != null)
        {
            map.put("maxval", NumberUtil.getObjLong(map.get("maxval")));
        }
        if (map.get("minval") != null)
        {
            map.put("minval", NumberUtil.getObjLong(map.get("minval")));
        }
        
        if (map.get("vrType") != null)
        {
            map.put("vrType", NumberUtil.getObjLong(map.get("vrType")));
        }
        if (map.get("deptcode") != null)
        {
            map.put("deptcode", NumberUtil.getObjLong(map.get("deptcode")));
        }
    }

    /**
     * 规则id
     */
    Long vrId;

    /**
     * 规则类型
     */
    Integer vrType;

    /**
     * 病区code
     */
    String deptcode;

    /**
     * 批次id
     */
    Long batchId;

    /**
     * 超限值
     */
    Long overrun;

    /**
     * 上限值
     */
    Long maxval;

    /**
     * 下限值
     */
    Long minval;

    /**
     * 修改人
     */
    Long vrUserId;

    /**
     * 修改时间
     */
    Date vrTime;
    
    public Long getVrId()
    {
        return vrId;
    }
    
    public void setVrId(Long vrId)
    {
        this.vrId = vrId;
    }
    
    public Integer getVrType()
    {
        return vrType;
    }
    
    public void setVrType(Integer vrType)
    {
        this.vrType = vrType;
    }
    
    public String getDeptcode()
    {
        return deptcode;
    }
    
    public void setDeptcode(String deptcode)
    {
        this.deptcode = deptcode;
    }
    
    public Long getBatchId()
    {
        return batchId;
    }
    
    public void setBatchId(Long batchId)
    {
        this.batchId = batchId;
    }
    
    public Long getOverrun()
    {
        return overrun;
    }
    
    public void setOverrun(Long overrun)
    {
        this.overrun = overrun;
    }
    
    public Long getMaxval()
    {
        return maxval;
    }
    
    public void setMaxval(Long maxval)
    {
        this.maxval = maxval;
    }
    
    public Long getMinval()
    {
        return minval;
    }
    
    public void setMinval(Long minval)
    {
        this.minval = minval;
    }
    
    public Long getVrUserId()
    {
        return vrUserId;
    }
    
    public void setVrUserId(Long vrUserId)
    {
        this.vrUserId = vrUserId;
    }
    
    public Date getVrTime()
    {
        return vrTime;
    }
    
    public void setVrTime(Date vrTime)
    {
        this.vrTime = vrTime;
    }
    
}
