package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
/**
 * 
 * 药品信息bean
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Medicine
{
    @XmlAttribute
    private String suspension = "false";
    
    @XmlAttribute
    private String judge = "true";
    
    /**
     * 组号
     */
    private String group_number = "";
    
    /**
     * 通用名
     */
    private String general_name = "";
    
    /**
     * 医院药品代码
     */
    private String license_number = "";
    
    /**
     * 商品名
     */
    private String medicine_name = "";
    
    /**
     * 单次量
     */
    private Single_dose single_dose = new Single_dose();
    
    /**
     * 频次代码
     */
    private String frequency = "";
    
    /**
     * 次数
     */
    private String times = "";
    
    /**
     * 单位（mg,g等）
     */
    private String unit = "";
    
    /**
     * 用药途径
     */
    private String administer_drugs = "";
    
    /**
     * 用药开始时间(YYYY-MM-DD HH:mm:SS)
     */
    private String begin_time = "";
    
    /**
     * 用药结束时间
     */
    private String end_time = "";
    
    /**
     * 医嘱时间
     */
    private String prescription_time = "";
    
    public String getSuspension()
    {
        return suspension;
    }
    
    public void setSuspension(String suspension)
    {
        this.suspension = suspension;
    }
    
    public String getJudge()
    {
        return judge;
    }
    
    public void setJudge(String judge)
    {
        this.judge = judge;
    }
    
    public String getGroup_number()
    {
        return group_number;
    }
    
    public void setGroup_number(String group_number)
    {
        this.group_number = group_number;
    }
    
    public String getGeneral_name()
    {
        return general_name;
    }
    
    public void setGeneral_name(String general_name)
    {
        this.general_name = general_name;
    }
    
    public String getLicense_number()
    {
        return license_number;
    }
    
    public void setLicense_number(String license_number)
    {
        this.license_number = license_number;
    }
    
    public String getMedicine_name()
    {
        return medicine_name;
    }
    
    public void setMedicine_name(String medicine_name)
    {
        this.medicine_name = medicine_name;
    }
    
    public Single_dose getSingle_dose()
    {
        return single_dose;
    }
    
    public void setSingle_dose(Single_dose single_dose)
    {
        this.single_dose = single_dose;
    }
    
    public String getFrequency()
    {
        return frequency;
    }
    
    public void setFrequency(String frequency)
    {
        this.frequency = frequency;
    }
    
    public String getTimes()
    {
        return times;
    }
    
    public void setTimes(String times)
    {
        this.times = times;
    }
    
    public String getUnit()
    {
        return unit;
    }
    
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    public String getAdminister_drugs()
    {
        return administer_drugs;
    }
    
    public void setAdminister_drugs(String administer_drugs)
    {
        this.administer_drugs = administer_drugs;
    }
    
    public String getBegin_time()
    {
        return begin_time;
    }
    
    public void setBegin_time(String begin_time)
    {
        this.begin_time = begin_time;
    }
    
    public String getEnd_time()
    {
        return end_time;
    }
    
    public void setEnd_time(String end_time)
    {
        this.end_time = end_time;
    }
    
    public String getPrescription_time()
    {
        return prescription_time;
    }
    
    public void setPrescription_time(String prescription_time)
    {
        this.prescription_time = prescription_time;
    }
}
