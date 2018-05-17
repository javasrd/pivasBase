package com.zc.pivas.docteradvice.bean;

import com.zc.pivas.docteradvice.entity.OtherRule;

import java.util.List;

/**
 * 
 * 其他规则bean
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class OtherRuleConfig
{
    
    public OtherRuleConfig()
    {
        
    }
    
    public OtherRuleConfig(List<OtherRule> otherRuleList)
    {
        if (otherRuleList != null && otherRuleList.size() > 0)
        {
            for (OtherRule otherRule : otherRuleList)
            {
                if (otherRule.getOrName().equals("treeRuleRun"))
                {
                    if (otherRule.getEnabled().intValue() == 1)
                    {
                        treeRuleRun = true;
                    }
                    else
                    {
                        treeRuleRun = false;
                    }
                }
                if (otherRule.getOrName().equals("kongPCRun"))
                {
                    if (otherRule.getEnabled().intValue() == 1)
                    {
                        kongPCRun = true;
                    }
                    else
                    {
                        kongPCRun = false;
                    }
                }
                if (otherRule.getOrName().equals("kongPCVolRun"))
                {
                    if (otherRule.getEnabled().intValue() == 1)
                    {
                        kongPCVolRun = true;
                    }
                    else
                    {
                        kongPCVolRun = false;
                    }
                }
                if (otherRule.getOrName().equals("oneMedToKong"))
                {
                    if (otherRule.getEnabled().intValue() == 1)
                    {
                        oneMedToKong = true;
                    }
                    else
                    {
                        oneMedToKong = false;
                    }
                }
                if (otherRule.getOrName().equals("volAutoUpDown"))
                {
                    if (otherRule.getEnabled().intValue() == 1)
                    {
                        volAutoUpDown = true;
                    }
                    else
                    {
                        volAutoUpDown = false;
                    }
                }
            }
        }
    }

    /**
     * 是否启用三大规则
     */
    boolean treeRuleRun = true;//是否启用三大规则

    /**
     * 空批是否参与批次优化
     */
    boolean kongPCRun = true;//空批是否参与批次优化

    /**
     * 空批是否计算容积？
     */
    boolean kongPCVolRun = true;//空批是否计算容积？

    /**
     * 单个药品是否落空批
     */
    boolean oneMedToKong = true;//单个药品是否落空批

    /**
     * 容积不够或超出，是否上下调整
     */
    boolean volAutoUpDown = true;//容积不够或超出，是否上下调整
    
    public boolean isTreeRuleRun()
    {
        return treeRuleRun;
    }
    
    public void setTreeRuleRun(boolean treeRuleRun)
    {
        this.treeRuleRun = treeRuleRun;
    }
    
    public boolean isKongPCRun()
    {
        return kongPCRun;
    }
    
    public void setKongPCRun(boolean kongPCRun)
    {
        this.kongPCRun = kongPCRun;
    }
    
    public boolean isKongPCVolRun()
    {
        return kongPCVolRun;
    }
    
    public void setKongPCVolRun(boolean kongPCVolRun)
    {
        this.kongPCVolRun = kongPCVolRun;
    }
    
    public boolean isOneMedToKong()
    {
        return oneMedToKong;
    }
    
    public void setOneMedToKong(boolean oneMedToKong)
    {
        this.oneMedToKong = oneMedToKong;
    }
    
    public boolean isVolAutoUpDown()
    {
        return volAutoUpDown;
    }
    
    public void setVolAutoUpDown(boolean volAutoUpDown)
    {
        this.volAutoUpDown = volAutoUpDown;
    }
    
}
