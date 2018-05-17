package com.zc.pivas.statistics.bean.druguse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 批次->药品使用统计
 *
 * @author  cacabin
 * @version  1.0
 */
public class Dept2DrugUseBarBean implements Serializable
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
     * 批次药品分类数量列表
     */
    private List<DrugClassCount> drugClassDeptCountList = new ArrayList<DrugClassCount>();
    
    public String getWardName()
    {
        return wardName;
    }
    
    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }
    
    public List<DrugClassCount> getDrugClassDeptCountList()
    {
        return drugClassDeptCountList;
    }
    
    public void setDrugClassDeptCountList(List<DrugClassCount> drugClassDeptCountList)
    {
        this.drugClassDeptCountList = drugClassDeptCountList;
    }
    
    public void addDrugClassDeptCountList(DrugClassCount drugClassCount)
    {
        this.drugClassDeptCountList.add(drugClassCount);
    }
    
    @Override
    public String toString()
    {
        return "Dept2DrugUseBarBean [wardName=" + wardName + ", drugClassDeptCountList=" + drugClassDeptCountList + "]";
    }
}
