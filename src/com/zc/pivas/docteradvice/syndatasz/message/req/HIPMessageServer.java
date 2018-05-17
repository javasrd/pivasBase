package com.zc.pivas.docteradvice.syndatasz.message.req;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * 
 * soap消息服务类
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HIPMessageServer
{
    /**
     * soap 中 HIPMessageServer namespace
     */
    public static final String NAMESPACE_OF_RECEIVEB2CORDER = "http://esb.ewell.cc";
    
    @XmlElement(name = "input", namespace = NAMESPACE_OF_RECEIVEB2CORDER)
    @XmlCDATA
    private String inPut;
    
    public String getInPut()
    {
        return inPut;
    }
    
    public void setInPut(String inPut)
    {
        this.inPut = inPut;
    }
    
}
