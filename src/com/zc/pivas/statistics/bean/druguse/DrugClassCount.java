package com.zc.pivas.statistics.bean.druguse;

import java.io.Serializable;

/**
 * 
 * 对应药品分类数量
 *
 * @author  cacabin
 * @version  1.0
 */
public class DrugClassCount implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 药品分类编码
     */
    private String drugClassCode;
    
    /**
     * 药品分类名称
     */
    private String drugClassName;
    
    /**
     * 统计数量
     */
    private Integer ststsCount;
    
    public String getDrugClassCode()
    {
        return drugClassCode;
    }
    
    public void setDrugClassCode(String drugClassCode)
    {
        this.drugClassCode = drugClassCode;
    }
    
    public String getDrugClassName()
    {
        return drugClassName;
    }
    
    public void setDrugClassName(String drugClassName)
    {
        this.drugClassName = drugClassName;
    }
    
    public Integer getStstsCount()
    {
        return ststsCount;
    }
    
    public void setStstsCount(Integer ststsCount)
    {
        this.ststsCount = ststsCount;
    }
    
    @Override
    public String toString()
    {
        return "DrugClassCount [drugClassCode=" + drugClassCode + ", drugClassName=" + drugClassName + ", ststsCount="
            + ststsCount + "]";
    }
    
}
