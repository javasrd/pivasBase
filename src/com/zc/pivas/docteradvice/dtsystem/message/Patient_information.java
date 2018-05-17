package com.zc.pivas.docteradvice.dtsystem.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * 
 * 病人信息
 *
 * @author  cacabin
 * @version  0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient_information
{
    /**
     * 体重
     */
    @XmlAttribute
    private String weight = "";
    
    /**
     * 身高
     */
    @XmlAttribute
    private String height = "";
    
    /**
     * 出生年月日
     */
    @XmlAttribute
    private String birth = "";
    
    /**
     * 病人
     */
    private String patient_name = "";
    
    /**
     * 病人性别
     */
    private String patient_sex = "";
    
    /**
     * 生理情况
     */
    private String physiological_statms = "";
    
    /**
     * 菌检结果
     */
    private String boacterioscopy_effect = "";
    
    /**
     * 血压
     */
    private String bloodpressure = "";
    
    /**
     * 肌酐清除率
     */
    private String liver_clean = "";
    
    /**
     * 孕妇怀孕时间
     */
    private String pregnant = "";
    
    /**
     * 怀孕时间计量单位
     */
    private String pdw = "";
    
    private Allergic_history allergic_history = new Allergic_history();
    
    private Diagnoses diagnoses = new Diagnoses();
    
    public String getWeight()
    {
        return weight;
    }
    
    public void setWeight(String weight)
    {
        this.weight = weight;
    }
    
    public String getHeight()
    {
        return height;
    }
    
    public void setHeight(String height)
    {
        this.height = height;
    }
    
    public String getBirth()
    {
        return birth;
    }
    
    public void setBirth(String birth)
    {
        this.birth = birth;
    }
    
    public String getPatient_name()
    {
        return patient_name;
    }
    
    public void setPatient_name(String patient_name)
    {
        this.patient_name = patient_name;
    }
    
    public String getPatient_sex()
    {
        return patient_sex;
    }
    
    public void setPatient_sex(String patient_sex)
    {
        this.patient_sex = patient_sex;
    }
    
    public Allergic_history getAllergic_history()
    {
        return allergic_history;
    }
    
    public void setAllergic_history(Allergic_history allergic_history)
    {
        this.allergic_history = allergic_history;
    }
    
    public Diagnoses getDiagnoses()
    {
        return diagnoses;
    }
    
    public void setDiagnoses(Diagnoses diagnoses)
    {
        this.diagnoses = diagnoses;
    }
    
    public String getPhysiological_statms()
    {
        return physiological_statms;
    }
    
    public void setPhysiological_statms(String physiological_statms)
    {
        this.physiological_statms = physiological_statms;
    }
    
    public String getBoacterioscopy_effect()
    {
        return boacterioscopy_effect;
    }
    
    public void setBoacterioscopy_effect(String boacterioscopy_effect)
    {
        this.boacterioscopy_effect = boacterioscopy_effect;
    }
    
    public String getBloodpressure()
    {
        return bloodpressure;
    }
    
    public void setBloodpressure(String bloodpressure)
    {
        this.bloodpressure = bloodpressure;
    }
    
    public String getLiver_clean()
    {
        return liver_clean;
    }
    
    public void setLiver_clean(String liver_clean)
    {
        this.liver_clean = liver_clean;
    }
    
    public String getPregnant()
    {
        return pregnant;
    }
    
    public void setPregnant(String pregnant)
    {
        this.pregnant = pregnant;
    }
    
    public String getPdw()
    {
        return pdw;
    }
    
    public void setPdw(String pdw)
    {
        this.pdw = pdw;
    }
    
}
