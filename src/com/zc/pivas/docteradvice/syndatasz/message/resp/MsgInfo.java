package com.zc.pivas.docteradvice.syndatasz.message.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
/**
 * 
 * 消息内容
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MsgInfo
{
    @XmlElement(name = "Msg")
    private List<Msg> msgList;
    
    public List<Msg> getMsgList()
    {
        return msgList;
    }
    
    public void setMsgList(List<Msg> msgList)
    {
        this.msgList = msgList;
    }
    
}
