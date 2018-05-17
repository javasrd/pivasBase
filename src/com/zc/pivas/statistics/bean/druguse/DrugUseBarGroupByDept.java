package com.zc.pivas.statistics.bean.druguse;

import java.io.Serializable;

/**
 * 
 * group by批次：药物统计柱状图
 *
 * @author  cacabin
 * @version  1.0
 */
public class DrugUseBarGroupByDept implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 病区名称
     */
    private String wardName;
    
    /**
     * 药品分类名称
     */
    private String drugClassName;
    
    /**
     * 统计数量
     */
    private int ststsCount;
    
    /**
     * 统计金额
     */
    private double ststsCost;
    
    public String getWardName()
    {
        return wardName;
    }
    
    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }
    
    public String getDrugClassName()
    {
        return drugClassName;
    }
    
    public void setDrugClassName(String drugClassName)
    {
        this.drugClassName = drugClassName;
    }
    
    public int getStstsCount()
    {
        return ststsCount;
    }
    
    public void setStstsCount(int ststsCount)
    {
        this.ststsCount = ststsCount;
    }
    
    public double getStstsCost()
    {
        return ststsCost;
    }
    
    public void setStstsCost(double ststsCost)
    {
        this.ststsCost = ststsCost;
    }
    
    @Override
    public String toString()
    {
        return "DrugUseBarGroupByDept [wardName=" + wardName + ", drugClassName=" + drugClassName + ", ststsCount="
            + ststsCount + ",ststsCost=" + ststsCost + "]";
    }
}
