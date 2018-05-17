package com.zc.pivas.docteradvice.syndatasz.message.resp.msg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * 单个医嘱消息bean
 *
 * @author  cacabin
 * @version  1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class YzRow
{
    /**
     * 医嘱号
     */
    @XmlElement(name = "Order_No")
    private String order_No;
    
    /**
     * 医嘱组号
     */
    @XmlElement(name = "Order_Group_No")
    private String order_Group_No;
    
    /**
     * 医嘱开立科室代码
     */
    @XmlElement(name = "Order_Open_Dept_Code")
    private String order_Open_Dept_Code;
    
    /**
     * 医嘱开立科室名称
     */
    @XmlElement(name = "Order_Open_Dept_Name")
    private String order_Open_Dept_Name;
    
    /**
     * 床号
     */
    @XmlElement(name = "Bed_No")
    private String bed_No;
    
    /**
     * 住院流水号
     */
    @XmlElement(name = "Inhosp_No")
    private String inhosp_No;
    
    /**
     * 住院索引号
     */
    @XmlElement(name = "Inhosp_Index_No")
    private String inhosp_Index_No;
    
    /**
     * 患者姓名
     */
    @XmlElement(name = "PAT_NAME")
    private String pat_Name;
    
    /**
     * 生理性别代码0 :女  1男
     */
    @XmlElement(name = "PHYSIC_SEX_CODE")
    private String physic_Sex_Code;
    
    /**
     * 出生日期yyyy-mm-dd
     */
    @XmlElement(name = "Date_Birth")
    private String date_Birth;
    
    /**
     * 年龄
     */
    @XmlElement(name = "Age")
    private String age;
    
    /**
     * 年龄单位，0代表天，1代表月，2代表年
     */
    @XmlElement(name = "Age_Unit")
    private String age_Unit;
    
    /**
     * 体重
     */
    @XmlElement(name = "Weight")
    private String weight;
    
    /**
     * 医嘱开立医生工号
     */
    @XmlElement(name = "Order_Open_DR_Code")
    private String order_Open_DR_Code;
    
    /**
     * 医嘱开立医生姓名
     */
    @XmlElement(name = "Order_Open_DR_Name")
    private String order_Open_DR_Name;
    
    /**
     * 录入医生工号
     */
    @XmlElement(name = "Record_DR_Code")
    private String record_DR_Code;
    
    /**
     * 录入医生姓名
     */
    @XmlElement(name = "Record_DR_Name")
    private String record_DR_Name;
    
    /**
     * 医嘱频次代码
     */
    @XmlElement(name = "Order_Frequency_Code")
    private String order_Frequency_Code;
    
    /**
     * 用药途径代码
     */
    @XmlElement(name = "Dose_Way_Code")
    private String dose_Way_Code;
    
    /**
     * 药品代码
     */
    @XmlElement(name = "Drug_Code")
    private String drug_Code;
    
    /**
     * 药品名称
     */
    @XmlElement(name = "Drug_Name")
    private String drug_Name;
    
    /**
     * 规格
     */
    @XmlElement(name = "Specifications")
    private String specifications;
    
    /**
     * 药品使用次剂量
     */
    @XmlElement(name = "Drug_Use_One_Dosage")
    private String drug_Use_One_Dosage;
    
    /**
     * 药品使用次剂量单位
     */
    @XmlElement(name = "Drug_Use_One_Dosage_Unit")
    private String drug_Use_One_Dosage_Unit;
    
    /**
     * 药品数量
     */
    @XmlElement(name = "Drug_Amount")
    private String drug_Amount;
    
    /**
     * 医嘱开始时间  YYYY-MM-DD HH:MM:SS
     */
    @XmlElement(name = "Order_Order_Date")
    private String order_Order_Date;
    
    /**
     * 医嘱停止时间 YYYY-MM-DD HH:MM:SS
     */
    @XmlElement(name = "Order_Stop_Date")
    private String order_Stop_Date;
    
    /**
     * 备注
     */
    @XmlElement(name = "Note")
    private String note;
    
    /**
     * 自备药标志
     */
    @XmlElement(name = "Self_Drug_Flag")
    private String self_Drug_Flag;
    
    /**
     * 营养液标志
     */
    @XmlElement(name = "Nutrition_Liquid_Flag")
    private String nutrition_Liquid_Flag;
    
    /**
     * 医嘱执行状态0：执行 1：停止
     */
    @XmlElement(name = "Order_Execute_Status")
    private String order_Execute_Status;
    
    /**
     * 药品产地代码
     */
    @XmlElement(name = "Drug_Place_Code")
    private String drug_Place_Code;
    
    /**
     * 医嘱类型 0:长嘱  1:短嘱
     */
    @XmlElement(name = "ORDER_CATEG_CODE")
    private String order_Categ_Code;
    
    /**
     * 滴速
     */
    @XmlElement(name = "IV_DROP_RATE")
    private String drop_Speed;
    
    /**
     * 医生医嘱确认时间
     */
    @XmlElement(name = "CONFIRM_DATE")
    private String confirm_date;
    
    /**
     * 医嘱首日用药次数
     */
    @XmlElement(name = "Firstusecount")
    private String firstUseCount;
    
    /**
     * 护士确认医嘱审核时间
     */
    @XmlElement(name = "CHECK_TIME")
    private String check_date;
    
    public String getCheck_date()
    {
        return check_date;
    }
    
    public void setCheck_date(String check_date)
    {
        this.check_date = check_date;
    }
    
    public String getFirstUseCount()
    {
        return firstUseCount;
    }
    
    public void setFirstUseCount(String firstUseCount)
    {
        this.firstUseCount = firstUseCount;
    }
    
    public String getConfirm_date()
    {
        return confirm_date;
    }
    
    public void setConfirm_date(String confirm_date)
    {
        this.confirm_date = confirm_date;
    }
    
    public String getOrder_No()
    {
        return order_No;
    }
    
    public void setOrder_No(String order_No)
    {
        this.order_No = order_No;
    }
    
    public String getOrder_Group_No()
    {
        return order_Group_No;
    }
    
    public void setOrder_Group_No(String order_Group_No)
    {
        this.order_Group_No = order_Group_No;
    }
    
    public String getOrder_Open_Dept_Code()
    {
        return order_Open_Dept_Code;
    }
    
    public void setOrder_Open_Dept_Code(String order_Open_Dept_Code)
    {
        this.order_Open_Dept_Code = order_Open_Dept_Code;
    }
    
    public String getOrder_Open_Dept_Name()
    {
        return order_Open_Dept_Name;
    }
    
    public void setOrder_Open_Dept_Name(String order_Open_Dept_Name)
    {
        this.order_Open_Dept_Name = order_Open_Dept_Name;
    }
    
    public String getBed_No()
    {
        return bed_No;
    }
    
    public void setBed_No(String bed_No)
    {
        this.bed_No = bed_No;
    }
    
    public String getInhosp_No()
    {
        return inhosp_No;
    }
    
    public void setInhosp_No(String inhosp_No)
    {
        this.inhosp_No = inhosp_No;
    }
    
    public String getInhosp_Index_No()
    {
        return inhosp_Index_No;
    }
    
    public void setInhosp_Index_No(String inhosp_Index_No)
    {
        this.inhosp_Index_No = inhosp_Index_No;
    }
    
    public String getPat_Name()
    {
        return pat_Name;
    }
    
    public void setPat_Name(String pat_Name)
    {
        this.pat_Name = pat_Name;
    }
    
    public String getPhysic_Sex_Code()
    {
        return physic_Sex_Code;
    }
    
    public void setPhysic_Sex_Code(String physic_Sex_Code)
    {
        this.physic_Sex_Code = physic_Sex_Code;
    }
    
    public String getDate_Birth()
    {
        return date_Birth;
    }
    
    public void setDate_Birth(String date_Birth)
    {
        this.date_Birth = date_Birth;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public String getAge_Unit()
    {
        return age_Unit;
    }
    
    public void setAge_Unit(String age_Unit)
    {
        this.age_Unit = age_Unit;
    }
    
    public String getWeight()
    {
        return weight;
    }
    
    public void setWeight(String weight)
    {
        this.weight = weight;
    }
    
    public String getOrder_Open_DR_Code()
    {
        return order_Open_DR_Code;
    }
    
    public void setOrder_Open_DR_Code(String order_Open_DR_Code)
    {
        this.order_Open_DR_Code = order_Open_DR_Code;
    }
    
    public String getOrder_Open_DR_Name()
    {
        return order_Open_DR_Name;
    }
    
    public void setOrder_Open_DR_Name(String order_Open_DR_Name)
    {
        this.order_Open_DR_Name = order_Open_DR_Name;
    }
    
    public String getRecord_DR_Code()
    {
        return record_DR_Code;
    }
    
    public void setRecord_DR_Code(String record_DR_Code)
    {
        this.record_DR_Code = record_DR_Code;
    }
    
    public String getRecord_DR_Name()
    {
        return record_DR_Name;
    }
    
    public void setRecord_DR_Name(String record_DR_Name)
    {
        this.record_DR_Name = record_DR_Name;
    }
    
    public String getOrder_Frequency_Code()
    {
        return order_Frequency_Code;
    }
    
    public void setOrder_Frequency_Code(String order_Frequency_Code)
    {
        this.order_Frequency_Code = order_Frequency_Code;
    }
    
    public String getDose_Way_Code()
    {
        return dose_Way_Code;
    }
    
    public void setDose_Way_Code(String dose_Way_Code)
    {
        this.dose_Way_Code = dose_Way_Code;
    }
    
    public String getDrug_Code()
    {
        return drug_Code;
    }
    
    public void setDrug_Code(String drug_Code)
    {
        this.drug_Code = drug_Code;
    }
    
    public String getDrug_Name()
    {
        return drug_Name;
    }
    
    public void setDrug_Name(String drug_Name)
    {
        this.drug_Name = drug_Name;
    }
    
    public String getSpecifications()
    {
        return specifications;
    }
    
    public void setSpecifications(String specifications)
    {
        this.specifications = specifications;
    }
    
    public String getDrug_Use_One_Dosage()
    {
        return drug_Use_One_Dosage;
    }
    
    public void setDrug_Use_One_Dosage(String drug_Use_One_Dosage)
    {
        this.drug_Use_One_Dosage = drug_Use_One_Dosage;
    }
    
    public String getDrug_Use_One_Dosage_Unit()
    {
        return drug_Use_One_Dosage_Unit;
    }
    
    public void setDrug_Use_One_Dosage_Unit(String drug_Use_One_Dosage_Unit)
    {
        this.drug_Use_One_Dosage_Unit = drug_Use_One_Dosage_Unit;
    }
    
    public String getDrug_Amount()
    {
        return drug_Amount;
    }
    
    public void setDrug_Amount(String drug_Amount)
    {
        this.drug_Amount = drug_Amount;
    }
    
    public String getOrder_Order_Date()
    {
        return order_Order_Date;
    }
    
    public void setOrder_Order_Date(String order_Order_Date)
    {
        this.order_Order_Date = order_Order_Date;
    }
    
    public String getOrder_Stop_Date()
    {
        return order_Stop_Date;
    }
    
    public void setOrder_Stop_Date(String order_Stop_Date)
    {
        this.order_Stop_Date = order_Stop_Date;
    }
    
    public String getNote()
    {
        return note;
    }
    
    public void setNote(String note)
    {
        this.note = note;
    }
    
    public String getSelf_Drug_Flag()
    {
        return self_Drug_Flag;
    }
    
    public void setSelf_Drug_Flag(String self_Drug_Flag)
    {
        this.self_Drug_Flag = self_Drug_Flag;
    }
    
    public String getNutrition_Liquid_Flag()
    {
        return nutrition_Liquid_Flag;
    }
    
    public void setNutrition_Liquid_Flag(String nutrition_Liquid_Flag)
    {
        this.nutrition_Liquid_Flag = nutrition_Liquid_Flag;
    }
    
    public String getOrder_Execute_Status()
    {
        return order_Execute_Status;
    }
    
    public void setOrder_Execute_Status(String order_Execute_Status)
    {
        this.order_Execute_Status = order_Execute_Status;
    }
    
    public String getDrug_Place_Code()
    {
        return drug_Place_Code;
    }
    
    public void setDrug_Place_Code(String drug_Place_Code)
    {
        this.drug_Place_Code = drug_Place_Code;
    }
    
    public String getOrder_Categ_Code()
    {
        return order_Categ_Code;
    }
    
    public void setOrder_Categ_Code(String order_Categ_Code)
    {
        this.order_Categ_Code = order_Categ_Code;
    }
    
    public String getDrop_Speed()
    {
        return drop_Speed;
    }
    
    public void setDrop_Speed(String drop_Speed)
    {
        this.drop_Speed = drop_Speed;
    }
    
}
