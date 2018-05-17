package com.zc.pivas.statistics.bean.druguse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 药物统计柱状图：按批次
 *
 * @author  cacabin
 * @version  1.0
 */
public class DrugUseBatchBarBean implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 药品名称列表
     */
    private List<String> drugClassList = new ArrayList<String>();
    
    /**
     * 批次名称列表
     */
    private List<String> batchNameList = new ArrayList<String>();
    
    /**
     * 药品分类数据
     */
    private List<DrugUse2BatchBarBean> drugUse2BatchList = new ArrayList<DrugUse2BatchBarBean>();
    
    /**
     * 药品金额缓存
     */
    private Map<String, String> costMap = new HashMap<String, String>();
    
    public List<String> getBatchNameList()
    {
        return batchNameList;
    }
    
    public void setBatchNameList(List<String> batchNameList)
    {
        this.batchNameList = batchNameList;
    }
    
    public List<DrugUse2BatchBarBean> getDrugUse2BatchList()
    {
        return drugUse2BatchList;
    }
    
    public void setDrugUse2BatchList(List<DrugUse2BatchBarBean> drugUse2BatchList)
    {
        this.drugUse2BatchList = drugUse2BatchList;
    }
    
    public List<String> getDrugClassList()
    {
        return drugClassList;
    }
    
    public void setDrugClassList(List<String> drugClassList)
    {
        this.drugClassList = drugClassList;
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
        return "DrugUseBatchBarBean [drugClassList=" + drugClassList + ", batchNameList=" + batchNameList
            + ", drugUse2BatchList=" + drugUse2BatchList + "]";
    }
}
