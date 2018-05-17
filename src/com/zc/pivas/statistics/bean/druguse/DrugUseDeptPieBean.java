package com.zc.pivas.statistics.bean.druguse;

import com.zc.pivas.statistics.bean.prescription.YDDeptPieBean;

/**
 * 
 * 药物使用统计：病区过滤
 *
 * @author  cacabin
 * @version  1.0
 */
public class DrugUseDeptPieBean extends YDDeptPieBean
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
