package com.zc.pivas.docteradvice.syndatasz.message.resp.msg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 * 
 * 消息头信息
 * <功能详细描述>
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Header
{
    private String msgId;
    
    private String version;
    
    private String msgName;
    
    private String sourceSysCode;
    
    private String targetSysCode;
    
    private String createTime;
    
    public String getMsgId()
    {
        return msgId;
    }
    
    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }
    
    public String getVersion()
    {
        return version;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public String getMsgName()
    {
        return msgName;
    }
    
    public void setMsgName(String msgName)
    {
        this.msgName = msgName;
    }
    
    public String getSourceSysCode()
    {
        return sourceSysCode;
    }
    
    public void setSourceSysCode(String sourceSysCode)
    {
        this.sourceSysCode = sourceSysCode;
    }
    
    public String getTargetSysCode()
    {
        return targetSysCode;
    }
    
    public void setTargetSysCode(String targetSysCode)
    {
        this.targetSysCode = targetSysCode;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
}
