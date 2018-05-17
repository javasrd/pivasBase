package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
/**
 * 
 * 医生信息bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Doctor_information
{
    /**
     * 工号
     */
    @XmlAttribute(name = "job_number")
    private String jobNumber = "";
    
    /**
     * 系统时间:2006-10-27 14:20:30
     */
    @XmlAttribute(name = "date")
    private String date = "";
    
    public String getJobNumber()
    {
        return jobNumber;
    }
    
    public void setJobNumber(String jobNumber)
    {
        this.jobNumber = jobNumber;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
}
