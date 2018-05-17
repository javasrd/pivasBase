package com.zc.pivas.docteradvice.syndatasz.message.req.ESBEntry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * ESB消息类
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ESBEntry")
public class ESBEntry
{
    @XmlElement(name = "AccessControl")
    private AccessControl accessControl;
    
    @XmlElement(name = "MessageHeader")
    private MessageESBEntryHeader messageHeader;
    
    @XmlElement(name = "MsgInfo")
    private MsgESBEntryInfo msgInfo;
    
    public MessageESBEntryHeader getMessageHeader()
    {
        return messageHeader;
    }
    
    public void setMessageHeader(MessageESBEntryHeader messageHeader)
    {
        this.messageHeader = messageHeader;
    }
    
    public AccessControl getAccessControl()
    {
        return accessControl;
    }
    
    public void setAccessControl(AccessControl accessControl)
    {
        this.accessControl = accessControl;
    }
    
    public MsgESBEntryInfo getMsgInfo()
    {
        return msgInfo;
    }
    
    public void setMsgInfo(MsgESBEntryInfo msgInfo)
    {
        this.msgInfo = msgInfo;
    }
    
}
