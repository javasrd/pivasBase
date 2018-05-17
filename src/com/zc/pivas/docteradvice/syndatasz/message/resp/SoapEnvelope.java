package com.zc.pivas.docteradvice.syndatasz.message.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * soap获取消息类
 * <一句话功能简述>
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapEnvelope
{
    /**
     * soap 中 envelope namespace
     */
    public static final String NAMESPACE_OF_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
    
    @XmlElement(name = "Body", namespace = NAMESPACE_OF_ENVELOPE)
    private SoapBody body;
    
    public SoapBody getBody()
    {
        return body;
    }
    
    public void setBody(SoapBody body)
    {
        this.body = body;
    }
    
}
