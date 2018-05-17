package com.zc.pivas.docteradvice.dtsystem.autocheck.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 * 
 * xml病人类
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient
{
    private String name = "";
    
    /**
     * 出生日期(YYYY-MM-DD)
     */
    private String birth = "";
    
    /**
     * 性别（男/女/未知）
     */
    private String sex = "";
    
    /**
     * 体重（单位：千克）
     */
    private String weight = "";
    
    /**
     * 身高（单位：厘米）
     */
    private String height = "";
    
    /**
     * 身份证号
     */
    private String id_card = "";
    
    /**
     * 病例卡号
     */
    private String medical_record = "";
    
    /**
     * 卡类型
     */
    private String card_type = "";
    
    /**
     * 卡号
     */
    private String card_code = "";
    
    /**
     * 时间单位（天、周、月）
     */
    private String pregnant_unit = "";
    
    /**
     * 怀孕时间
     */
    private String pregnant = "";
    
    /**
     * 过敏信息
     */
    private Allergic_data allergic_data = new Allergic_data();
    
    /**
     * 诊断信息
     */
    private Diagnose_data diagnose_data = new Diagnose_data();
    
    public Allergic_data getAllergic_data()
    {
        return allergic_data;
    }
    
    public void setAllergic_data(Allergic_data allergic_data)
    {
        this.allergic_data = allergic_data;
    }
    
    public Diagnose_data getDiagnose_data()
    {
        return diagnose_data;
    }
    
    public void setDiagnose_data(Diagnose_data diagnose_data)
    {
        this.diagnose_data = diagnose_data;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getBirth()
    {
        return birth;
    }
    
    public void setBirth(String birth)
    {
        this.birth = birth;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
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
    
    public String getId_card()
    {
        return id_card;
    }
    
    public void setId_card(String id_card)
    {
        this.id_card = id_card;
    }
    
    public String getMedical_record()
    {
        return medical_record;
    }
    
    public void setMedical_record(String medical_record)
    {
        this.medical_record = medical_record;
    }
    
    public String getCard_type()
    {
        return card_type;
    }
    
    public void setCard_type(String card_type)
    {
        this.card_type = card_type;
    }
    
    public String getCard_code()
    {
        return card_code;
    }
    
    public void setCard_code(String card_code)
    {
        this.card_code = card_code;
    }
    
    public String getPregnant_unit()
    {
        return pregnant_unit;
    }
    
    public void setPregnant_unit(String pregnant_unit)
    {
        this.pregnant_unit = pregnant_unit;
    }
    
    public String getPregnant()
    {
        return pregnant;
    }
    
    public void setPregnant(String pregnant)
    {
        this.pregnant = pregnant;
    }
    
}
