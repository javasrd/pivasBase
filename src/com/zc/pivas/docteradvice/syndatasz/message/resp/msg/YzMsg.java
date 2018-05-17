package com.zc.pivas.docteradvice.syndatasz.message.resp.msg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * 医嘱消息bean
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class YzMsg
{
    @XmlElement(name = "body")
    private YzBody body;
    
    @XmlElement(name = "head")
    private Header head;
    
    public YzBody getBody()
    {
        return body;
    }
    
    public void setBody(YzBody body)
    {
        this.body = body;
    }
    
    public Header getHead()
    {
        return head;
    }
    
    public void setHead(Header head)
    {
        this.head = head;
    }
    
}
