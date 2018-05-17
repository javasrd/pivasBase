package com.zc.pivas.docteradvice.bean;

import java.io.Serializable;

/**
 * 
 * 医嘱主表简化实体类
 *
 * @author  cacabin
 * @version  0.1
 */
public class DoctorAdviceMinBean implements Serializable
{
    String yz_actOrderNo;
    /**
     * 药品名称
     */
    String yz_medicamentsName;

    /**
     * 药品规格
     */
    String yz_specifications;

    /**
     * 药品规格
     */
    String yz_dose;

    /**
     * 药品单次剂量单位
     */
    String yz_doseUnit;

    /**
     * 药品数量
     */
    String yz_quantity;

    /**
     * 包装单位
     */
    String yz_medicamentsPackingUnit;
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public String getYz_medicamentsName()
    {
        return yz_medicamentsName;
    }
    
    public void setYz_medicamentsName(String yz_medicamentsName)
    {
        this.yz_medicamentsName = yz_medicamentsName;
    }
    
    public String getYz_specifications()
    {
        return yz_specifications;
    }
    
    public void setYz_specifications(String yz_specifications)
    {
        this.yz_specifications = yz_specifications;
    }
    
    public String getYz_dose()
    {
        return yz_dose;
    }
    
    public void setYz_dose(String yz_dose)
    {
        this.yz_dose = yz_dose;
    }
    
    public String getYz_doseUnit()
    {
        return yz_doseUnit;
    }
    
    public void setYz_doseUnit(String yz_doseUnit)
    {
        this.yz_doseUnit = yz_doseUnit;
    }
    
    public String getYz_quantity()
    {
        return yz_quantity;
    }
    
    public void setYz_quantity(String yz_quantity)
    {
        this.yz_quantity = yz_quantity;
    }
    
    public String getYz_medicamentsPackingUnit()
    {
        return yz_medicamentsPackingUnit;
    }
    
    public void setYz_medicamentsPackingUnit(String yz_medicamentsPackingUnit)
    {
        this.yz_medicamentsPackingUnit = yz_medicamentsPackingUnit;
    }

    public String getYz_actOrderNo()
    {
        return yz_actOrderNo;
    }

    public void setYz_actOrderNo(String yz_actOrderNo)
    {
        this.yz_actOrderNo = yz_actOrderNo;
    }
    
}
