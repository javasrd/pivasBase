package com.zc.pivas.backup.bean;

/**
 * 
 * 服务节点bean
 *
 * @author  cacabin
 * @version  1.0
 */
public class ServerNodeBean
{
    private Integer ID;
    private String IP;
    private String name;
    private Integer flag;
    private String port;
    private String domainName;
    public Integer getID()
    {
        return ID;
    }
    public void setID(Integer iD)
    {
        ID = iD;
    }
    public String getIP()
    {
        return IP;
    }
    public void setIP(String iP)
    {
        IP = iP;
    }
    public Integer getFlag()
    {
        return flag;
    }
    public void setFlag(Integer flag)
    {
        this.flag = flag;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getPort()
    {
        return port;
    }
    public void setPort(String port)
    {
        this.port = port;
    }
    public String getDomainName()
    {
        return domainName;
    }
    public void setDomainName(String domainName)
    {
        this.domainName = domainName;
    }
}