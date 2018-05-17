package com.zc.pivas.docteradvice.bean;

import com.zc.pivas.docteradvice.entity.VolumeRules;

/**
 * 
 * 容积规则与批次关联bean
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class VolumeRulesWithBatch extends VolumeRules
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 病区名称
     */
    String deptname;

    /**
     * 批次名称
     */
    String batchName;
    
    public String getDeptname()
    {
        return deptname;
    }
    
    public void setDeptname(String deptname)
    {
        this.deptname = deptname;
    }
    
    public String getBatchName()
    {
        return batchName;
    }
    
    public void setBatchName(String batchName)
    {
        this.batchName = batchName;
    }

}
