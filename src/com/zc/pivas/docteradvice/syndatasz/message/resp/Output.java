package com.zc.pivas.docteradvice.syndatasz.message.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * 
 * 输出流对象
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Output
{
    @XmlElement(name = "ESBEntry")
    private ESBEntry esbEntry;
    
    public ESBEntry getEsbEntry()
    {
        return esbEntry;
    }
    
    public void setEsbEntry(ESBEntry esbEntry)
    {
        this.esbEntry = esbEntry;
    }
    
}
