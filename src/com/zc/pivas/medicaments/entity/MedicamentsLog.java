package com.zc.pivas.medicaments.entity;

import com.zc.base.common.dao.AbstractDO;

/**
 * 药品修改日志实体类 
 *
 * @author Jack.M
 * @version 1.0
 */
public class MedicamentsLog extends AbstractDO
{
    
    private static final long serialVersionUID = 1L;
    
    /** ID */
    private String id;
    
    /** 药品名称 */
    private String drugName;
    
    /** 药品编码 */
    private String drugCode;
    
    /** 药品分类ID */
    private String categoryId;
    
    /** 药品产地 */
    private String drugPlace;
    
    /** 药品分类名称 */
    private String categoryName;
    
    /** 药品产地编码 */
    private String drugPlaceCode;
    
    /**
     * 上次药品库存
     */
    private String stock_last;
    
    /**
     * 库存修改人
     */
    private String operator;
    
    /**
     * 速查码
     */
    private String checkCode;
    
    /**
     * 修改时间
     */
    private String updateDate;
    
    /**
     * 当前库存
     */
    private String stock_now;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getDrugName()
    {
        return drugName;
    }
    
    public void setDrugName(String drugName)
    {
        this.drugName = drugName;
    }
    
    public String getDrugCode()
    {
        return drugCode;
    }
    
    public void setDrugCode(String drugCode)
    {
        this.drugCode = drugCode;
    }
    
    public String getCategoryId()
    {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }
    
    public String getDrugPlace()
    {
        return drugPlace;
    }
    
    public void setDrugPlace(String drugPlace)
    {
        this.drugPlace = drugPlace;
    }
    
    public String getCategoryName()
    {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    public String getDrugPlaceCode()
    {
        return drugPlaceCode;
    }
    
    public void setDrugPlaceCode(String drugPlaceCode)
    {
        this.drugPlaceCode = drugPlaceCode;
    }
    
    public String getStock_last()
    {
        return stock_last;
    }
    
    public void setStock_last(String stock_last)
    {
        this.stock_last = stock_last;
    }
    
    public String getOperator()
    {
        return operator;
    }
    
    public void setOperator(String operator)
    {
        this.operator = operator;
    }
    
    public String getCheckCode()
    {
        return checkCode;
    }
    
    public void setCheckCode(String checkCode)
    {
        this.checkCode = checkCode;
    }
    
    public String getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }
    
    public String getStock_now()
    {
        return stock_now;
    }
    
    public void setStock_now(String stock_now)
    {
        this.stock_now = stock_now;
    }
}