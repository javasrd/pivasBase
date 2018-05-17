package com.zc.pivas.docteradvice.entity;

import com.zc.base.common.util.StrUtil;
import com.zc.base.sys.common.util.NumberUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 
 * 药物优先级bean
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class PriorityRules implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public PriorityRules()
    {
        
    }
    
    public PriorityRules(Map<String, Object> map)
    {
        prType = NumberUtil.getObjInt(map.get("prType"));
        deptcode = StrUtil.getObjStr(map.get("deptcode"));
        medicId = StrUtil.getObjStr(map.get("medicId"));
        batchId = NumberUtil.getObjLong(map.get("batchId"));
        prOrder = NumberUtil.getObjInt(map.get("prOrder"));
        prUserId = NumberUtil.getObjLong(map.get("prUserId"));
        medicType = NumberUtil.getObjInt(map.get("medicType"));
    }

    /**
     * 规则id
     */
    Long prId;

    /**
     * 规则类型
     */
    Integer prType;

    /**
     * 病区code
     */
    String deptcode;

    /**
     * 药品编码
     */
    String medicId;
    
    /**
     * 药品类型
     */
    Integer medicType;

    /**
     * 批次id
     */
    Long batchId;

    /**
     * 排序
     */
    Integer prOrder;

    /**
     * 修改人
     */
    Long prUserId;

    /**
     * 修改时间
     */
    Date prTime;

    /**
     * 是否使用
     */
    Integer enabled;
    
    public Long getPrId()
    {
        return prId;
    }
    
    public void setPrId(Long prId)
    {
        this.prId = prId;
    }
    
    public Integer getPrType()
    {
        return prType;
    }
    
    public void setPrType(Integer prType)
    {
        this.prType = prType;
    }
    
    public String getDeptcode()
    {
        return deptcode;
    }
    
    public void setDeptcode(String deptcode)
    {
        this.deptcode = deptcode;
    }
    
    public String getMedicId()
    {
        return medicId;
    }

    public void setMedicId(String medicId)
    {
        this.medicId = medicId;
    }

    public Integer getMedicType()
    {
        return medicType;
    }

    public void setMedicType(Integer medicType)
    {
        this.medicType = medicType;
    }

    public Integer getPrOrder()
    {
        return prOrder;
    }
    
    public void setPrOrder(Integer prOrder)
    {
        this.prOrder = prOrder;
    }
    
    public Long getPrUserId()
    {
        return prUserId;
    }
    
    public void setPrUserId(Long prUserId)
    {
        this.prUserId = prUserId;
    }
    
    public Date getPrTime()
    {
        return prTime;
    }
    
    public void setPrTime(Date prTime)
    {
        this.prTime = prTime;
    }

    public Long getBatchId()
    {
        return batchId;
    }

    public void setBatchId(Long batchId)
    {
        this.batchId = batchId;
    }

    public Integer getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
}
