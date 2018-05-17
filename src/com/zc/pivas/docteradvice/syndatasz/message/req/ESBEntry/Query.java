package com.zc.pivas.docteradvice.syndatasz.message.req.ESBEntry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
/**
 * 
 * ESB查询bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Query
{
    @XmlAttribute
    private String item = "";
    
    @XmlAttribute
    private String compy = "";
    
    @XmlAttribute
    private String value = "";
    
    @XmlAttribute
    private String splice = "";
    
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public String getItem()
    {
        return item;
    }
    
    public void setItem(String item)
    {
        this.item = item;
    }
    
    public String getCompy()
    {
        return compy;
    }
    
    public void setCompy(String compy)
    {
        this.compy = compy;
    }
    
    public String getSplice()
    {
        return splice;
    }
    
    public void setSplice(String splice)
    {
        this.splice = splice;
    }
    
}
