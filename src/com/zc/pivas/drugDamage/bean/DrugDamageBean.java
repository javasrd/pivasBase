package com.zc.pivas.drugDamage.bean;

import java.io.Serializable;

public class DrugDamageBean implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -8104934669347594883L;
    
    private Long gid;
    
    private String drugCode;
    
    private String[] drugCodes;
    
    private String drugName;
    
    private String[] drugNames;
    
    private String specifications;
    
    private String drugPlace;
    
    private String[] drugPlaces;
    
    private Integer damageProblem;
    
    private Integer damageLink;
    
    private Integer quantity;
    
    private String registName;
    
    private String registTime;
    
    private String reserve1;
    
    private String reserve2;
    
    private String reserve3;
    
    public String[] getDrugNames()
    {
        return drugNames;
    }

    public void setDrugNames(String[] drugNames)
    {
        this.drugNames = drugNames;
    }
    
    public String[] getDrugCodes()
    {
        return drugCodes;
    }

    public void setDrugCodes(String[] drugCodes)
    {
        this.drugCodes = drugCodes;
    }

    public String[] getDrugPlaces()
    {
        return drugPlaces;
    }

    public void setDrugPlaces(String[] drugPlaces)
    {
        this.drugPlaces = drugPlaces;
    }

    public Long getGid()
    {
        return gid;
    }

    public void setGid(Long gid)
    {
        this.gid = gid;
    }

    public String getDrugCode()
    {
        return drugCode;
    }

    public void setDrugCode(String drugCode)
    {
        this.drugCode = drugCode;
    }

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

    public String getDrugPlace()
    {
        return drugPlace;
    }

    public void setDrugPlace(String drugPlace)
    {
        this.drugPlace = drugPlace;
    }

    public Integer getDamageProblem()
    {
        return damageProblem;
    }

    public void setDamageProblem(Integer damageProblem)
    {
        this.damageProblem = damageProblem;
    }

    public Integer getDamageLink()
    {
        return damageLink;
    }

    public void setDamageLink(Integer damageLink)
    {
        this.damageLink = damageLink;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public String getRegistName()
    {
        return registName;
    }

    public void setRegistName(String registName)
    {
        this.registName = registName;
    }

    public String getRegistTime()
    {
        return registTime;
    }

    public void setRegistTime(String registTime)
    {
        this.registTime = registTime;
    }

    public String getReserve1()
    {
        return reserve1;
    }

    public void setReserve1(String reserve1)
    {
        this.reserve1 = reserve1;
    }

    public String getReserve2()
    {
        return reserve2;
    }

    public void setReserve2(String reserve2)
    {
        this.reserve2 = reserve2;
    }

    public String getReserve3()
    {
        return reserve3;
    }

    public void setReserve3(String reserve3)
    {
        this.reserve3 = reserve3;
    }
}
