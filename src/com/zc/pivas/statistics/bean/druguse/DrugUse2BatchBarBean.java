package com.zc.pivas.statistics.bean.druguse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 药品统计->批次
 *
 * @author  cacabin
 * @version  1.0
 */
public class DrugUse2BatchBarBean implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 分类名称
     */
    private String drugClassName;
    
    /**
     * 批次药单数量列表
     */
    private List<Integer> ydCountList = new ArrayList<Integer>();
    
    public String getDrugClassName()
    {
        return drugClassName;
    }
    
    public void setDrugClassName(String drugClassName)
    {
        this.drugClassName = drugClassName;
    }
    
    public List<Integer> getYdCountList()
    {
        return ydCountList;
    }
    
    public void setYdCountList(List<Integer> ydCountList)
    {
        this.ydCountList = ydCountList;
    }
    
    public void addYdCountList(Integer count)
    {
        this.ydCountList.add(count);
    }
}
