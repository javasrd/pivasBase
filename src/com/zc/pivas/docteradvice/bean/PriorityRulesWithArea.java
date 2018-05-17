package com.zc.pivas.docteradvice.bean;

import com.zc.pivas.docteradvice.entity.PriorityRules;

/**
 * 
 * 单个病区药物优先级规则
 * 
 *
 * @author  cacabin
 * @version  1.0
 */
public class PriorityRulesWithArea extends PriorityRules
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 病区名称
     */
    String deptname;

    /**
     * 药品名称
     */
    String medicName;

    /**
     * 药品编码
     */
    String medicCode;

    /**
     * 药品分类
     */
    String categoryId;
    
    public String getDeptname()
    {
        return deptname;
    }
    
    public void setDeptname(String deptname)
    {
        this.deptname = deptname;
    }
    
    public String getMedicName()
    {
        return medicName;
    }
    
    public void setMedicName(String medicName)
    {
        this.medicName = medicName;
    }

    public String getMedicCode()
    {
        return medicCode;
    }

    public void setMedicCode(String medicCode)
    {
        this.medicCode = medicCode;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }
    
    
    
}
