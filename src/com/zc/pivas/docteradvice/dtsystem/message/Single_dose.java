package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
/**
 * 
 * 单次剂量bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Single_dose
{
    @XmlAttribute
    private String coef = "1";
    
    @XmlValue
    private String dose = "";
    
    public String getCoef()
    {
        return coef;
    }
    
    public void setCoef(String coef)
    {
        this.coef = coef;
    }
    
    public String getDose()
    {
        return dose;
    }
    
    public void setDose(String dose)
    {
        this.dose = dose;
    }
    
}
