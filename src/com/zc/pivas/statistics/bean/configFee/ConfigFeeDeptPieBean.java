package com.zc.pivas.statistics.bean.configFee;
/**
 * 
 * 配置费报表 病区bean
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeDeptPieBean
{
    /**
     * 名称
     */
    private String name;
    
    /**
     * 统计数量
     */
    private Float value;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Float getValue()
    {
        return value;
    }
    
    public void setValue(Float value)
    {
        this.value = value;
    }
    
}
