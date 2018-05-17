package com.zc.pivas.docteradvice.syndatasz.message.req.ESBEntry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * 
 * 用户授权bean
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AccessControl
{
    @XmlElement(name = "UserName")
    private String userName = "jp";
    
    @XmlElement(name = "Password")
    private String password = "jp";
    
    @XmlElement(name = "Fid")
    private String fid;
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getFid()
    {
        return fid;
    }
    
    public void setFid(String fid)
    {
        this.fid = fid;
    }
}
