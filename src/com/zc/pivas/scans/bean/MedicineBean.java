package com.zc.pivas.scans.bean;

/**
 * 
 * 扫描药品实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class MedicineBean
{
    
    /**
     * 药单ID
     */
    private Integer ydID;
    
    /**
     * 药品编码
     */
    private String medicamentsCode;
    
    /**
     * 医嘱编码,此字段须与HIS医嘱信息中编码一致
     */
    private String actOrderNo;
    
    /**
     * 父医嘱编码或组编码
     */
    private String parentNo;
    
    /**
     * 医嘱的药品名称
     */
    private String drugName;
    
    /**
     * 医嘱的药品规格
     */
    private String specifications;
    
    /**
     * 医嘱的药品单次剂量
     */
    private String dose;
    
    /**
     * 医嘱的药品单次剂量单位
     */
    private String doseUnit;
    
    /**
     * 药品数量
     */
    private String quantity;
    
    /**
     * 包装单位
     */
    private String medicamentsPackingUnit;
    
    private String pidrqzxbc;
    
    /**
     * 使用日期
     */
    private String useDate;
    
    public String getDrugName()
    {
        return drugName;
    }
    
    public void setDrugName(String drugName)
    {
        this.drugName = drugName;
    }
    
    public String getSpecifications()
    {
        return specifications;
    }
    
    public void setSpecifications(String specifications)
    {
        this.specifications = specifications;
    }
    
    public String getDose()
    {
        return dose + doseUnit;
    }
    
    public void setDose(String dose)
    {
        this.dose = dose;
    }
    
    public String getDoseUnit()
    {
        return doseUnit;
    }
    
    public void setDoseUnit(String doseUnit)
    {
        this.doseUnit = doseUnit;
    }
    
    public String getQuantityOnly()
    {
        return quantity;
    }
    
    public String getQuantity()
    {
        return quantity + medicamentsPackingUnit;
    }
    
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    
    public String getMedicamentsPackingUnit()
    {
        return medicamentsPackingUnit;
    }
    
    public void setMedicamentsPackingUnit(String medicamentsPackingUnit)
    {
        this.medicamentsPackingUnit = medicamentsPackingUnit;
    }
    
    public String getParentNo()
    {
        return parentNo;
    }
    
    public void setParentNo(String parentNo)
    {
        this.parentNo = parentNo;
    }
    
    public String getUseDate()
    {
        return useDate;
    }
    
    public void setUseDate(String useDate)
    {
        this.useDate = useDate;
    }
    
    public String getActOrderNo()
    {
        return actOrderNo;
    }
    
    public void setActOrderNo(String actOrderNo)
    {
        this.actOrderNo = actOrderNo;
    }
    
    public Integer getYdID()
    {
        return ydID;
    }
    
    public void setYdID(Integer ydID)
    {
        this.ydID = ydID;
    }
    
    public String getMedicamentsCode()
    {
        return medicamentsCode;
    }
    
    public void setMedicamentsCode(String medicamentsCode)
    {
        this.medicamentsCode = medicamentsCode;
    }
    
    public String getPidrqzxbc()
    {
        return pidrqzxbc;
    }
    
    public void setPidrqzxbc(String pidrqzxbc)
    {
        this.pidrqzxbc = pidrqzxbc;
    }
    
    public String getDoseOnly()
    {
        return dose;
    }
    
}
