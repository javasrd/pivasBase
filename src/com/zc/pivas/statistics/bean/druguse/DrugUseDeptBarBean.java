package com.zc.pivas.statistics.bean.druguse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 药物统计柱状图：按病区
 *
 * @author  cacabin
 * @version  1.0
 */
public class DrugUseDeptBarBean implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 药品分类名称列表
     */
    private List<String> drugClassList = new ArrayList<String>();
    
    /**
     * 病区名称列表
     */
    private List<String> wardNameList = new ArrayList<String>();
    
    /**
     * 药品分类数据
     */
    private List<DrugUse2DeptBarBean> drugUse2DeptList = new ArrayList<DrugUse2DeptBarBean>();
    
    /**
     * 药品金额缓存
     */
    private Map<String, String> costMap = new HashMap<String, String>();
    
    public List<String> getDrugClassList()
    {
        return drugClassList;
    }
    
    public void setDrugClassList(List<String> drugClassList)
    {
        this.drugClassList = drugClassList;
    }
    
    public List<String> getWardNameList()
    {
        return wardNameList;
    }
    
    public void setWardNameList(List<String> wardNameList)
    {
        this.wardNameList = wardNameList;
    }
    
    public List<DrugUse2DeptBarBean> getDrugUse2DeptList()
    {
        return drugUse2DeptList;
    }
    
    public void setDrugUse2DeptList(List<DrugUse2DeptBarBean> drugUse2DeptList)
    {
        this.drugUse2DeptList = drugUse2DeptList;
    }
    
    public Map<String, String> getCostMap()
    {
        return costMap;
    }
    
    public void setCostMap(Map<String, String> costMap)
    {
        this.costMap = costMap;
    }
    
    @Override
    public String toString()
    {
        return "DrugUseDeptBarBean [drugClassList=" + drugClassList + ", wardNameList=" + wardNameList
            + ", drugUse2DeptList=" + drugUse2DeptList + "]";
    }
}
