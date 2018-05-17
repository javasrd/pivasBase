package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * 处方信息
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Prescription
{
    /**
     * 处方号
     */
    private String id = "";
    
    /**
     * 理由
     */
    private String reason = "";
    
    /**
     * 是否当前处方（0/1）
     */
    private String is_current = "";
    
    /**
     * 长期医嘱L/临时医嘱T
     */
    private String pres_type = "";
    
    /**
     * 处方时间（YYYY-MM-DD HH:mm:SS）
     */
    private String pres_time = "";
    
    /**
     * 药品信息
     */
    @XmlElement(name = "medicine_data")
    private Medicine_data medicine_data = new Medicine_data();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getReason()
    {
        return reason;
    }
    
    public void setReason(String reason)
    {
        this.reason = reason;
    }
    
    public String getIs_current()
    {
        return is_current;
    }
    
    public void setIs_current(String is_current)
    {
        this.is_current = is_current;
    }
    
    public String getPres_type()
    {
        return pres_type;
    }
    
    public void setPres_type(String pres_type)
    {
        this.pres_type = pres_type;
    }
    
    public String getPres_time()
    {
        return pres_time;
    }
    
    public void setPres_time(String pres_time)
    {
        this.pres_time = pres_time;
    }
    
    public Medicine_data getMedicine_data()
    {
        return medicine_data;
    }
    
    public void setMedicine_data(Medicine_data medicine_data)
    {
        this.medicine_data = medicine_data;
    }
}
