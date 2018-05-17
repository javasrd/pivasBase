package com.zc.pivas.scans.bean;

/**
 * 
 * 瓶签扫描结果
 *
 * @author  cacabin
 * @version  1.0
 */
public class BottleLabelResult
{
    /**
     * 药单瓶签表的主键
     */
    private String ydID;
    
    /**
     * 扫描日期
     */
    private String smRQ;
    
    /**
     * 扫描结果
     */
    private Integer smJG;
    
    /**
     * 扫描失败原因
     */
    private String smSBYY;
    
    /**
     * 扫描类型 0：入仓扫描  1：出仓扫描
     */
    private String smLX;
    
    private Integer zxbc;
    
    private String operator;
    
    private String ydpq;

    public String getYdID()
    {
        return ydID;
    }
    
    public void setYdID(String ydID)
    {
        this.ydID = ydID;
    }
    
    public String getSmRQ()
    {
        return smRQ;
    }
    
    public void setSmRQ(String smRQ)
    {
        this.smRQ = smRQ;
    }
    
    public Integer getSmJG()
    {
        return smJG;
    }
    
    public void setSmJG(Integer smJG)
    {
        this.smJG = smJG;
    }
    
    public String getSmSBYY()
    {
        return smSBYY;
    }
    
    public void setSmSBYY(String smSBYY)
    {
        this.smSBYY = smSBYY;
    }
    
    public String getSmLX()
    {
        return smLX;
    }
    
    public void setSmLX(String smLX)
    {
        this.smLX = smLX;
    }
    
    public Integer getZxbc()
    {
        return zxbc;
    }
    
    public void setZxbc(Integer zxbc)
    {
        this.zxbc = zxbc;
    }

    /**
     * @return 返回 operator
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * @param
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * @return 返回 ydpq
     */
    public String getYdpq()
    {
        return ydpq;
    }

    /**
     * @param
     */
    public void setYdpq(String ydpq)
    {
        this.ydpq = ydpq;
    }
    
}
