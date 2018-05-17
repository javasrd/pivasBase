package com.zc.pivas.statistics.bean.prescription;
/**
 * 
 * 药单按批次统计
 *
 * @author jagger
 * @version 1.0
 */
public class YDBatchPieBean
{
    private Integer statusKey;
    /**
     * 批次名称
     */
    private String name;
    
    /**
     * 统计数量
     */
    private Integer value;
    
    /**
     * 批次
     */
    private Integer zxbc;
    
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
    
    public Integer getZxbc()
    {
        return zxbc;
    }
    
    public void setZxbc(Integer zxbc)
    {
        this.zxbc = zxbc;
    }
    
}
