package com.zc.pivas.scans.bean;
/**
 * 
 * 扫描查询参数bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class ScansSearchBean
{
    /**
     * 病区名称
     */
    private String deptName;
    
    /**
     * 批次名称
     */
    private String batchName;
    
    /**
     * 执行批次，对应批次表的主键
     */
    private String zXBC;
    
    /**
     * 扫描类型 0：入仓扫描  1：出仓扫描
     */
    private String smLX;
    
    /**
     * 患者姓名
     */
    private String patName;
    
    /**
     * 床号
     */
    private String bedNO;
    
    private String qryRQ;
    
    public String getQryRQ()
    {
        return qryRQ;
    }
    
    public void setQryRQ(String qryRQ)
    {
        this.qryRQ = qryRQ;
    }
    
    /**
     * 药单瓶签的唯一编号，审核通过后自动生成，打印时写入二维码并显示在瓶签上
     */
    private String yDPQ;
    
    public String getDeptName()
    {
        return deptName;
    }
    
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    
    public String getBatchName()
    {
        return batchName;
    }
    
    public void setBatchName(String batchName)
    {
        this.batchName = batchName;
    }
    
    public String getzXBC()
    {
        return zXBC;
    }
    
    public void setzXBC(String zXBC)
    {
        this.zXBC = zXBC;
    }
    
    public String getSmLX()
    {
        return smLX;
    }
    
    public void setSmLX(String smLX)
    {
        this.smLX = smLX;
    }
    
    public String getPatName()
    {
        return patName;
    }
    
    public void setPatName(String patName)
    {
        this.patName = patName;
    }
    
    public String getBedNO()
    {
        return bedNO;
    }
    
    public void setBedNO(String bedNO)
    {
        this.bedNO = bedNO;
    }
    
    public String getyDPQ()
    {
        return yDPQ;
    }
    
    public void setyDPQ(String yDPQ)
    {
        this.yDPQ = yDPQ;
    }
    
}
