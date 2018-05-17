package com.zc.pivas.scans.bean;

/**
 * 
 * 描码批次
 *
 * @author  cacabin
 * @version  1.0
 */
public class ScansBatchBean
{
    /**
     * 执行批次，对应批次表的主键
     */
    private String zXBC;
    
    /**
     * 进仓:待扫描 数量
     */
    private Integer enterUnScansCount = 0;
    
    /**
     * 进仓:已扫描 数量
     */
    private Integer enterFinishScansCount = 0;
    
    /**
     * 进仓:扫描 总数量
     */
    private Integer enterSumScansCount = 0;
    
    /**
     * 出仓:待扫描 数量
     */
    private Integer outUnScansCount = 0;
    
    /**
     * 出仓:已扫描 数量
     */
    private Integer outFinishScansCount = 0;
    
    /**
     * 出仓:扫描 总数量
     */
    private Integer outSumScansCount = 0;
    
    /**
     * 批次名称
     */
    private String batchName;
    
    /**
     * 病区名称
     */
    private String deptName;
    
    public String getzXBC()
    {
        return zXBC;
    }
    
    public void setzXBC(String zXBC)
    {
        this.zXBC = zXBC;
    }
    
    public Integer getEnterUnScansCount()
    {
        return enterUnScansCount;
    }
    
    public void setEnterUnScansCount(Integer enterUnScansCount)
    {
        this.enterUnScansCount = enterUnScansCount;
    }
    
    public Integer getEnterSumScansCount()
    {
        return enterSumScansCount;
    }
    
    public void addEnterSumScansCount(Integer enterSumScansCount)
    {
        this.enterSumScansCount += enterSumScansCount;
    }
    
    public void setEnterSumScansCount(Integer enterSumScansCount)
    {
        this.enterSumScansCount = enterSumScansCount;
    }
    
    public Integer getOutUnScansCount()
    {
        return outUnScansCount;
    }
    
    public void setOutUnScansCount(Integer outUnScansCount)
    {
        this.outUnScansCount = outUnScansCount;
    }
    
    public Integer getOutSumScansCount()
    {
        return outSumScansCount;
    }
    
    public void setOutSumScansCount(Integer outSumScansCount)
    {
        this.outSumScansCount = outSumScansCount;
    }
    
    public void addOutSumScansCount(Integer outSumScansCount)
    {
        this.outSumScansCount += outSumScansCount;
    }
    
    public String getBatchName()
    {
        return batchName;
    }
    
    public void setBatchName(String batchName)
    {
        this.batchName = batchName;
    }
    
    public Integer getEnterFinishScansCount()
    {
        return enterFinishScansCount;
    }
    
    public void setEnterFinishScansCount(Integer enterFinishScansCount)
    {
        this.enterFinishScansCount = enterFinishScansCount;
    }
    
    public void addEnterFinishScansCount(Integer enterFinishScansCount)
    {
        this.enterFinishScansCount += enterFinishScansCount;
    }
    
    public Integer getOutFinishScansCount()
    {
        return outFinishScansCount;
    }
    
    public void setOutFinishScansCount(Integer outFinishScansCount)
    {
        this.outFinishScansCount = outFinishScansCount;
    }
    
    public void addOutFinishScansCount(Integer outFinishScansCount)
    {
        this.outFinishScansCount += outFinishScansCount;
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
