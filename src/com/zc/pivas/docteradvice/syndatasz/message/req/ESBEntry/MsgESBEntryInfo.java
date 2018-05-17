package com.zc.pivas.docteradvice.syndatasz.message.req.ESBEntry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * 
 * ESB实体bean
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MsgESBEntryInfo
{
    @XmlElement(name = "onceFlag")
    private String onceFlag = "0";
    
    @XmlElement(name = "startNum")
    private String startNum = "1";
    
    @XmlElement(name = "endNum")
    private String endNum = "";
    
    @XmlElement(name = "Msg")
    private String Msg;
    
    @XmlElement(name = "query")
    private Query query;
    
    public String getOnceFlag()
    {
        return onceFlag;
    }
    
    public void setOnceFlag(String onceFlag)
    {
        this.onceFlag = onceFlag;
    }
    
    public String getStartNum()
    {
        return startNum;
    }
    
    public void setStartNum(String startNum)
    {
        this.startNum = startNum;
    }
    
    public String getEndNum()
    {
        return endNum;
    }
    
    public void setEndNum(String endNum)
    {
        this.endNum = endNum;
    }
    
    public String getMsg()
    {
        return Msg;
    }
    
    public void setMsg(String msg)
    {
        Msg = msg;
    }
    
    public Query getQuery()
    {
        return query;
    }
    
    public void setQuery(Query query)
    {
        this.query = query;
    }
    
}
