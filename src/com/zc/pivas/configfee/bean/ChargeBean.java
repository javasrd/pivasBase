package com.zc.pivas.configfee.bean;

import java.io.Serializable;

/**
 * 
 * 费用实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class ChargeBean implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    String categoryName;
    
    String parentNo;
    
    String costname;
    
    String rulename;
    
    String drugname;
    
    String dose;
    
    String doseUnit;
    
    String quantity;
    
    String doctorName;
    
    String czymc;
    
    String price;
    
    String checkname;
    
    Long ydPzfId;
    
    Integer pzfzt;
    
    //Long ydId;
    Integer amount;
    
    String pzfsqrq;
    
    String pzfsbyy;
    
    String costcode;
    
    String caseID;
    
    Integer bcID;
    
    String deptCode;
    
    Long pqRefFeeID;
    
    Integer gid;
    
    Integer configfeeruleid;
    
    String pidrqzxbc;
    
    public String getCategoryName()
    {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    public String getParentNo()
    {
        return parentNo;
    }
    
    public void setParentNo(String parentNo)
    {
        this.parentNo = parentNo;
    }
    
    public String getCostname()
    {
        return costname;
    }
    
    public void setCostname(String costname)
    {
        this.costname = costname;
    }
    
    public String getRulename()
    {
        return rulename;
    }
    
    public void setRulename(String rulename)
    {
        this.rulename = rulename;
    }
    
    public String getDrugname()
    {
        return drugname;
    }
    
    public void setDrugname(String drugname)
    {
        this.drugname = drugname;
    }
    
    public String getDose()
    {
        return dose;
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
    
    public String getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    
    public String getDoctorName()
    {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }
    
    public String getCzymc()
    {
        return czymc;
    }
    
    public void setCzymc(String czymc)
    {
        this.czymc = czymc;
    }
    
    public String getPrice()
    {
        return price;
    }
    
    public void setPrice(String price)
    {
        this.price = price;
    }
    
    public String getCheckname()
    {
        return checkname;
    }
    
    public void setCheckname(String checkname)
    {
        this.checkname = checkname;
    }
    
    public Long getYdPzfId()
    {
        return ydPzfId;
    }
    
    public void setYdPzfId(Long ydPzfId)
    {
        this.ydPzfId = ydPzfId;
    }
    
    public Integer getPzfzt()
    {
        return pzfzt;
    }
    
    public void setPzfzt(Integer pzfzt)
    {
        this.pzfzt = pzfzt;
    }
    
    public String getPzfsqrq()
    {
        return pzfsqrq;
    }
    
    public void setPzfsqrq(String pzfsqrq)
    {
        this.pzfsqrq = pzfsqrq;
    }
    
    public String getPzfsbyy()
    {
        return pzfsbyy;
    }
    
    public void setPzfsbyy(String pzfsbyy)
    {
        this.pzfsbyy = pzfsbyy;
    }
    
    public String getCostcode()
    {
        return costcode;
    }
    
    public void setCostcode(String costcode)
    {
        this.costcode = costcode;
    }
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    public Integer getBcID()
    {
        return bcID;
    }
    
    public void setBcID(Integer bcID)
    {
        this.bcID = bcID;
    }
    
    public String getDeptCode()
    {
        return deptCode;
    }
    
    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }
    
    public Long getPqRefFeeID()
    {
        return pqRefFeeID;
    }
    
    public void setPqRefFeeID(Long pqRefFeeID)
    {
        this.pqRefFeeID = pqRefFeeID;
    }
    
    public String getCaseID()
    {
        return caseID;
    }
    
    public void setCaseID(String caseID)
    {
        this.caseID = caseID;
    }
    
    public Integer getConfigfeeruleid()
    {
        return configfeeruleid;
    }
    
    public void setConfigfeeruleid(Integer configfeeruleid)
    {
        this.configfeeruleid = configfeeruleid;
    }
    
    public Integer getGid()
    {
        return gid;
    }
    
    public void setGid(Integer gid)
    {
        this.gid = gid;
    }
    
    public String getPidrqzxbc()
    {
        return pidrqzxbc;
    }
    
    public void setPidrqzxbc(String pidrqzxbc)
    {
        this.pidrqzxbc = pidrqzxbc;
    }
    
}
