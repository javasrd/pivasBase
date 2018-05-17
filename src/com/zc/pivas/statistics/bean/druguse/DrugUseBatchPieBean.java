package com.zc.pivas.statistics.bean.druguse;

import com.zc.pivas.statistics.bean.prescription.YDBatchPieBean;

/**
 * 
 * 药物使用统计：饼图，根据批次统计数据
 *
 * @author  cacabin
 * @version  1.0
 */
public class DrugUseBatchPieBean extends YDBatchPieBean
{
    private String ststsCost;
    
    public String getStstsCost()
    {
        return ststsCost;
    }
    
    public void setStstsCost(String ststsCost)
    {
        this.ststsCost = ststsCost;
    }
}
