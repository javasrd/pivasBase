package com.zc.pivas.statistics.bean.configFee;

/**
 * 
 * 配置费状态类
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeStatusBean
{
    /**
     * 批次名称
     */
    private String batchNAME;
    
    /**
     * 统计数量
     */
    private Float ststsCount;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 配置费的类别，0:普通药物 1:抗生素 2:细胞毒药物 3:TPN
     */
    private Integer statusKey;
    
    /**
     * 批次
     */
    private Integer zxbc;
    
    /**
     * 病区名称
     */
    private String deptName;
    
    public String getBatchNAME()
    {
        return batchNAME;
    }
    
    public void setBatchNAME(String batchNAME)
    {
        this.batchNAME = batchNAME;
    }
    
    public Float getStstsCount()
    {
        return ststsCount;
    }
    
    public void setStstsCount(Float ststsCount)
    {
        this.ststsCount = ststsCount;
    }
    
    public String getStatusName()
    {
        return statusName;
    }
    
    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }
    
    public Integer getStatusKey()
    {
        return statusKey;
    }
    
    public void setStatusKey(Integer statusKey)
    {
        this.statusKey = statusKey;
    }
    
    public Integer getZxbc()
    {
        return zxbc;
    }
    
    public void setZxbc(Integer zxbc)
    {
        this.zxbc = zxbc;
    }
    
    public String getDeptName()
    {
        return deptName;
    }
    
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    
}
