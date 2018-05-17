package com.zc.pivas.docteradvice.syndatasz.message.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * 
 * soap消息体
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapBody
{
    /**
     * soap 中 HIPMessageServer namespace
     */
    public static final String NAMESPACE_OF_RECEIVEB2CORDER = "http://esb.ewell.cc";
    
    @XmlElement(name = "HIPMessageServerResponse", namespace = NAMESPACE_OF_RECEIVEB2CORDER)
    private HIPMessageServerResponse hipMessageServerResponse;
    
    public HIPMessageServerResponse getHipMessageServerResponse()
    {
        return hipMessageServerResponse;
    }
    
    public void setHipMessageServerResponse(HIPMessageServerResponse hipMessageServerResponse)
    {
        this.hipMessageServerResponse = hipMessageServerResponse;
    }
    
}
