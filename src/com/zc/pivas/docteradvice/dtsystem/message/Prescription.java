package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
/**
 * 
 * 处方信息bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Prescription
{
    @XmlAttribute
    private String id = "";
    
    @XmlAttribute
    private String type = "";
    
    private Medicine medicine = new Medicine();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public Medicine getMedicine()
    {
        return medicine;
    }
    
    public void setMedicine(Medicine medicine)
    {
        this.medicine = medicine;
    }
    
}
