package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 * 
 * xml药品类
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Medicine
{
    /**
     * 商品名
     */
    private String name = "";
    
    /**
     * 医院药品代码
     */
    private String his_code = "";
    
    /**
     * 医保代码
     */
    private String insur_code = "";
    
    /**
     * 批准文号
     */
    private String approval = "";
    
    /**
     * 规格
     */
    private String spec = "";
    
    /**
     * 组号
     */
    private String group = "";
    
    /**
     * 用药理由
     */
    private String reason = "";
    
    /**
     * 单次量单位
     */
    private String dose_unit = "";
    
    /**
     * 单次量
     */
    private String dose = "";
    
    /**
     * 频次代码
     */
    private String freq = "";
    
    /**
     * 给药途径代码
     */
    private String administer = "";
    
    /**
     * （住院）用药开始时间
     */
    private String begin_time = "";
    
    /**
     * (住院)用药结束时间
     */
    private String end_time = "";
    
    /**
     * 服药天数（门诊）
     */
    private String days = "";
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getHis_code()
    {
        return his_code;
    }
    
    public void setHis_code(String his_code)
    {
        this.his_code = his_code;
    }
    
    public String getInsur_code()
    {
        return insur_code;
    }
    
    public void setInsur_code(String insur_code)
    {
        this.insur_code = insur_code;
    }
    
    public String getApproval()
    {
        return approval;
    }
    
    public void setApproval(String approval)
    {
        this.approval = approval;
    }
    
    public String getSpec()
    {
        return spec;
    }
    
    public void setSpec(String spec)
    {
        this.spec = spec;
    }
    
    public String getGroup()
    {
        return group;
    }
    
    public void setGroup(String group)
    {
        this.group = group;
    }
    
    public String getReason()
    {
        return reason;
    }
    
    public void setReason(String reason)
    {
        this.reason = reason;
    }
    
    public String getDose_unit()
    {
        return dose_unit;
    }
    
    public void setDose_unit(String dose_unit)
    {
        this.dose_unit = dose_unit;
    }
    
    public String getDose()
    {
        return dose;
    }
    
    public void setDose(String dose)
    {
        this.dose = dose;
    }
    
    public String getFreq()
    {
        return freq;
    }
    
    public void setFreq(String freq)
    {
        this.freq = freq;
    }
    
    public String getAdminister()
    {
        return administer;
    }
    
    public void setAdminister(String administer)
    {
        this.administer = administer;
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
    
    public String getDays()
    {
        return days;
    }
    
    public void setDays(String days)
    {
        this.days = days;
    }
}
