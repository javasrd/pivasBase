package com.zc.pivas.statistics.bean.prescription;
/**
 * 
 * 药单病区统计类
 *
 * @author jagger
 * @version 1.0
 */
public class YDDeptPieBean
{
    private Integer statusKey;
    /**
     * 名称
     */
    private String name;
    
    /**
     * 统计数量
     */
    private Integer value;
    
    public Integer getStatusKey()
    {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey)
    {
        this.statusKey = statusKey;
    }

    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getValue()
    {
        return value;
    }
    
    public void setValue(Integer value)
    {
        this.value = value;
    }
    
}
