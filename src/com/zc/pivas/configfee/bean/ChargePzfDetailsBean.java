package com.zc.pivas.configfee.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * 配置费详情bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class ChargePzfDetailsBean implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    private Long pqRefFeeID;
    
    private int pzfzt;
    
    private Date pzfSQRQ;

    public Long getPqRefFeeID()
    {
        return pqRefFeeID;
    }

    public void setPqRefFeeID(Long pqRefFeeID)
    {
        this.pqRefFeeID = pqRefFeeID;
    }

    public int getPzfzt()
    {
        return pzfzt;
    }

    public void setPzfzt(int pzfzt)
    {
        this.pzfzt = pzfzt;
    }

    public Date getPzfSQRQ()
    {
        return pzfSQRQ;
    }

    public void setPzfSQRQ(Date pzfSQRQ)
    {
        this.pzfSQRQ = pzfSQRQ;
    }
    
}
