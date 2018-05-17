package com.zc.pivas.scans.bean;

import java.io.Serializable;
/**
 * 
 * 扫描实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class CommBean implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    private String scansType;
    
    private String scansSN;
    
    public String getScansType()
    {
        return scansType;
    }
    
    public void setScansType(String scansType)
    {
        this.scansType = scansType;
    }
    
    public String getScansSN()
    {
        return scansSN;
    }
    
    public void setScansSN(String scansSN)
    {
        this.scansSN = scansSN;
    }
    
}
