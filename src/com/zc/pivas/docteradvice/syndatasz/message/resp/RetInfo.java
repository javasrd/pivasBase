package com.zc.pivas.docteradvice.syndatasz.message.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * 
 * 返回信息实体bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RetInfo
{
    @XmlElement(name = "RetCode")
    private String retCode;
    
    @XmlElement(name = "RetCon")
    private String retCon;
    
    public String getRetCode()
    {
        return retCode;
    }
    
    public void setRetCode(String retCode)
    {
        this.retCode = retCode;
    }
    
    public String getRetCon()
    {
        return retCon;
    }
    
    public void setRetCon(String retCon)
    {
        this.retCon = retCon;
    }
    
}
