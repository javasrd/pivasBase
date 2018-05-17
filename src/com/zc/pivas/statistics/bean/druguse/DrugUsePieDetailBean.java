package com.zc.pivas.statistics.bean.druguse;

import java.io.Serializable;

/**
 * 
 * 饼图批次：选中一批次后数据统计
 *
 * @author  cacabin
 * @version  1.0
 */
public class DrugUsePieDetailBean implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 某一药品分类统计数量
     */
    private Integer ststsCount;
    
    /**
     * 某一药品分类金额
     */
    private Double ststsCost;
    
    /**
     * 分类名称
     */
    private String drugClassName;
    
    public Integer getStstsCount()
    {
        return ststsCount;
    }
    
    public void setStstsCount(Integer ststsCount)
    {
        this.ststsCount = ststsCount;
    }
    
    public String getDrugClassName()
    {
        return drugClassName;
    }
    
    public void setDrugClassName(String drugClassName)
    {
        this.drugClassName = drugClassName;
    }
    
    public Double getStstsCost()
    {
        return ststsCost;
    }
    
    public void setStstsCost(Double ststsCost)
    {
        this.ststsCost = ststsCost;
    }
    
    @Override
    public String toString()
    {
        return "DrugUseBatchPieDetailBean [ststsCount=" + ststsCount + ", drugClassName=" + drugClassName + "]";
    }
    
}
