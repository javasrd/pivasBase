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
public class Batch2DrugUseBarBean implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 批次
     */
    private Integer zxbc;
    
    /**
     * 批次名称
     */
    private String batchNAME;
    
    /**
     * 批次药品分类数量列表
     */
    private List<DrugClassCount> drugClassBatchCountList = new ArrayList<DrugClassCount>();
    
    public Integer getZxbc()
    {
        return zxbc;
    }
    
    public void setZxbc(Integer zxbc)
    {
        this.zxbc = zxbc;
    }
    
    public String getBatchNAME()
    {
        return batchNAME;
    }
    
    public void setBatchNAME(String batchNAME)
    {
        this.batchNAME = batchNAME;
    }
    
    public List<DrugClassCount> getDrugClassBatchCountList()
    {
        return drugClassBatchCountList;
    }
    
    public void setDrugClassBatchCountList(List<DrugClassCount> drugClassBatchCountList)
    {
        this.drugClassBatchCountList = drugClassBatchCountList;
    }
    
    public void addDrugClassBatchCountList(DrugClassCount drugClassCount)
    {
        this.drugClassBatchCountList.add(drugClassCount);
    }
    
    @Override
    public String toString()
    {
        return "DrugUse2BatchBarBean [zxbc=" + zxbc + ", batchNAME=" + batchNAME + ", drugClassBatchCountList="
            + drugClassBatchCountList + "]";
    }
    
}
