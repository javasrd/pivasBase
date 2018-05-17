package com.zc.pivas.drugDamage.bean;

import java.io.Serializable;

public class DrugDamageProblemOrLink implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 749724160822845018L;
    
    private Integer code;
    
    private String description;

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
}
