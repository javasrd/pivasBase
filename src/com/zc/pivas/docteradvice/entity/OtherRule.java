package com.zc.pivas.docteradvice.entity;

import com.zc.base.common.util.StrUtil;
import com.zc.base.sys.common.util.NumberUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * 其他规则bean
 *
 * @author  cacabin
 * @version  0.1
 */
public class OtherRule implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    public OtherRule()
    {
        
    }
    
    public OtherRule(Map<String, Object> map)
    {
        orId = NumberUtil.getObjLong(map.get("orId"));
        orType = NumberUtil.getObjInt(map.get("orType"));
        orName = StrUtil.getObjStr(map.get("orName"));
        orDesc = StrUtil.getObjStr(map.get("orDesc"));
        orSort = NumberUtil.getObjInt(map.get("orSort"));
        enabled = NumberUtil.getObjInt(map.get("enabled"));
    }

    /**
     * 规则id
     */
    Long orId;

    /**
     * 规则类型
     */
    Integer orType;

    /**
     * 规则名称
     */
    String orName;

    /**
     * 规则描述
     */
    String orDesc;

    /**
     * 规则排序
     */
    Integer orSort;

    /**
     * 是否启用
     */
    Integer enabled;

    /**
     * 
     */
    public Long getOrId()
    {
        return orId;
    }

    public void setOrId(Long orId)
    {
        this.orId = orId;
    }

    public Integer getOrType()
    {
        return orType;
    }

    public void setOrType(Integer orType)
    {
        this.orType = orType;
    }

    public String getOrName()
    {
        return orName;
    }

    public void setOrName(String orName)
    {
        this.orName = orName;
    }

    public String getOrDesc()
    {
        return orDesc;
    }

    public void setOrDesc(String orDesc)
    {
        this.orDesc = orDesc;
    }

    public Integer getOrSort()
    {
        return orSort;
    }

    public void setOrSort(Integer orSort)
    {
        this.orSort = orSort;
    }

    public Integer getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }
    
}
