package com.zc.pivas.docteradvice.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * 医嘱同步数据表
 *
 * @author  cacabin
 * @version  1.0
 */
public class SynDoctorAdviceBean implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    private String id = "";
    
    /**
     * 医嘱的唯一性标志
     */
    private String orderNo = "";
    
    /**
     * 成组医嘱的唯一性标志
     */
    private String orderGroupNo = "";
    
    /**
     * 开立医嘱的科室在医疗机构的科室代码
     */
    private String orderOpenDeptCode = "";
    
    /**
     * 开立医嘱的科室在医疗机构的科室名称
     */
    private String orderOpenDeptName = "";
    
    /**
     * 患者住院期间，所住床位对应的编号
     */
    private String bedNo = "";
    
    /**
     * 住院流水号
     */
    private String inhospNo = "";
    
    /**
     * 患者住院期间，住院对应的索引号，每次就诊相同，类似于就诊卡号
     */
    private String inhospIndexNo = "";
    
    /**
     * 医嘱开立医生工号
     */
    private String orderOpendrCode = "";
    
    /**
     * 医嘱开立医生姓名
     */
    private String orderOpendrName = "";
    
    /**
     * 录入医生工号
     */
    private String recordDrCode = "";
    
    /**
     * 录入医生姓名
     */
    private String recordDrName = "";
    
    /**
     * 医嘱频次代码,对应医嘱频次信息
     */
    private String orderFrequencyCode = "";
    
    /**
     * 用药途径代码,对应用药途径
     */
    private String doseWayCode = "";
    
    /**
     * 药品代码,对应药品字典
     */
    private String drugCode = "";
    
    /**
     * 药品名称
     */
    private String drugName = "";
    
    /**
     * 规格,如0.25kg
     */
    private String specifications = "";
    
    /**
     * 药品使用次剂量
     */
    private String drugUseOneDosAge = "";
    
    /**
     * 药品使用次剂量单位
     */
    private String drugUseOneDosAgeUnit = "";
    
    /**
     * 药品数量
     */
    private String drugAmount = "";
    
    /**
     * 医嘱开立时间
     */
    private Timestamp orderOrderDate;
    
    /**
     * 医嘱停止时间
     */
    private Timestamp orderStopDate;
    
    /**
     * 备注
     */
    private String note = "";
    
    /**
     * 自备药标志,0是，1不是, 默认不是
     */
    private Integer selfDrugFlag;
    
    /**
     * 营养液标志,0是，1不是, 默认不是
     */
    private Integer nutritionliquidFlag;
    
    /**
     * 医嘱执行状态,0：执行 1：停止 2：撤销
     */
    private Integer orderExecuteStatus;
    
    /**
     * 药品产地代码
     */
    private String drugPlaceCode = "";
    
    /**
     * 0(新增)、1(变更)
     */
    private int action;
    
    /**
     * 执行日期
     */
    private String zxrq = "";
    
    /**
     * 执行时间
     */
    private String zxsj = "";
    
    /**
     * 医嘱类型 0:长期 1:短期
     */
    private int yzlx;
    
    /**
     * 包装单位
     */
    private String medicamentsPackingUnit = "";
    
    /**
     * 患者姓名
     */
    private String patName = "";
    
    /**
     * 生理性别代码0:女 1：男
     */
    private String sex = "";
    
    /**
     * "yyyy-mm-dd"(出生日期，HIS过来的格式是yyyymmdd)
     */
    private Timestamp birth;
    
    /**
     * 年龄
     */
    private String age = "";
    
    /**
     * 年龄单位，0代表天，1代表月，2代表年，HIS过来的是D（天）、M（月）、Y（年）
     */
    private Integer ageUnit;
    
    /**
     * 病人体重kg
     */
    private String avdp = "";
    
    /**
     * "100ml/min"(医嘱滴速)
     */
    private String dropSpeed = "";
    
    /**
     * 同步时间 "yyyy-mm-dd"
     */
    private String synData = "";
    
    /**
     * 医生医嘱确认时间
     */
    private String confirmDate = "";
    
    /**
     * 医嘱首日用药次数
     */
    private String firstUseCount = "";
    
    /**
     * 护士医嘱审核时间
     */
    private String checkDate = "";
    
    public String getCheckDate()
    {
        return checkDate;
    }

    public void setCheckDate(String checkDate)
    {
        this.checkDate = checkDate;
    }

    public String getFirstUseCount()
    {
        return firstUseCount;
    }

    public void setFirstUseCount(String firstUseCount)
    {
        this.firstUseCount = firstUseCount;
    }
    
    public String getConfirmDate()
    {
        return confirmDate;
    }
    
    public void setConfirmDate(String confirmDate)
    {
        this.confirmDate = confirmDate;
    }
    
    public String getSynData()
    {
        return synData;
    }
    
    public void setSynData(String synData)
    {
        this.synData = synData;
    }
    
    public String getPatName()
    {
        return patName;
    }
    
    public void setPatName(String patName)
    {
        this.patName = patName;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public Timestamp getBirth()
    {
        return birth;
    }
    
    public void setBirth(Timestamp birth)
    {
        this.birth = birth;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public Integer getAgeUnit()
    {
        return ageUnit;
    }
    
    public void setAgeUnit(Integer ageUnit)
    {
        this.ageUnit = ageUnit;
    }
    
    public String getAvdp()
    {
        return avdp;
    }
    
    public void setAvdp(String avdp)
    {
        this.avdp = avdp;
    }
    
    public String getDropSpeed()
    {
        return dropSpeed;
    }
    
    public void setDropSpeed(String dropSpeed)
    {
        this.dropSpeed = dropSpeed;
    }
    
    public int getYzlx()
    {
        return yzlx;
    }
    
    public void setYzlx(int yzlx)
    {
        this.yzlx = yzlx;
    }
    
    public String getMedicamentsPackingUnit()
    {
        return medicamentsPackingUnit;
    }
    
    public void setMedicamentsPackingUnit(String medicamentsPackingUnit)
    {
        this.medicamentsPackingUnit = medicamentsPackingUnit;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderNo()
    {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }
    
    public String getOrderGroupNo()
    {
        return orderGroupNo;
    }
    
    public void setOrderGroupNo(String orderGroupNo)
    {
        this.orderGroupNo = orderGroupNo;
    }
    
    public String getOrderOpenDeptCode()
    {
        return orderOpenDeptCode;
    }
    
    public void setOrderOpenDeptCode(String orderOpenDeptCode)
    {
        this.orderOpenDeptCode = orderOpenDeptCode;
    }
    
    public String getOrderOpenDeptName()
    {
        return orderOpenDeptName;
    }
    
    public void setOrderOpenDeptName(String orderOpenDeptName)
    {
        this.orderOpenDeptName = orderOpenDeptName;
    }
    
    public String getBedNo()
    {
        return bedNo;
    }
    
    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }
    
    public String getInhospNo()
    {
        return inhospNo;
    }
    
    public void setInhospNo(String inhospNo)
    {
        this.inhospNo = inhospNo;
    }
    
    public String getInhospIndexNo()
    {
        return inhospIndexNo;
    }
    
    public void setInhospIndexNo(String inhospIndexNo)
    {
        this.inhospIndexNo = inhospIndexNo;
    }
    
    public String getOrderOpendrCode()
    {
        return orderOpendrCode;
    }
    
    public void setOrderOpendrCode(String orderOpendrCode)
    {
        this.orderOpendrCode = orderOpendrCode;
    }
    
    public String getOrderOpendrName()
    {
        return orderOpendrName;
    }
    
    public void setOrderOpendrName(String orderOpendrName)
    {
        this.orderOpendrName = orderOpendrName;
    }
    
    public String getRecordDrCode()
    {
        return recordDrCode;
    }
    
    public void setRecordDrCode(String recordDrCode)
    {
        this.recordDrCode = recordDrCode;
    }
    
    public String getRecordDrName()
    {
        return recordDrName;
    }
    
    public void setRecordDrName(String recordDrName)
    {
        this.recordDrName = recordDrName;
    }
    
    public String getOrderFrequencyCode()
    {
        return orderFrequencyCode;
    }
    
    public void setOrderFrequencyCode(String orderFrequencyCode)
    {
        this.orderFrequencyCode = orderFrequencyCode;
    }
    
    public String getDoseWayCode()
    {
        return doseWayCode;
    }
    
    public void setDoseWayCode(String doseWayCode)
    {
        this.doseWayCode = doseWayCode;
    }
    
    public String getDrugCode()
    {
        return drugCode;
    }
    
    public void setDrugCode(String drugCode)
    {
        this.drugCode = drugCode;
    }
    
    public String getDrugName()
    {
        return drugName;
    }
    
    public void setDrugName(String drugName)
    {
        this.drugName = drugName;
    }
    
    public String getSpecifications()
    {
        return specifications;
    }
    
    public void setSpecifications(String specifications)
    {
        this.specifications = specifications;
    }
    
    public String getDrugUseOneDosAge()
    {
        return drugUseOneDosAge;
    }
    
    public void setDrugUseOneDosAge(String drugUseOneDosAge)
    {
        this.drugUseOneDosAge = drugUseOneDosAge;
    }
    
    public String getDrugUseOneDosAgeUnit()
    {
        return drugUseOneDosAgeUnit;
    }
    
    public void setDrugUseOneDosAgeUnit(String drugUseOneDosAgeUnit)
    {
        this.drugUseOneDosAgeUnit = drugUseOneDosAgeUnit;
    }
    
    public String getDrugAmount()
    {
        return drugAmount;
    }
    
    public void setDrugAmount(String drugAmount)
    {
        this.drugAmount = drugAmount;
    }
    
    public Timestamp getOrderOrderDate()
    {
        return orderOrderDate;
    }
    
    public void setOrderOrderDate(Timestamp orderOrderDate)
    {
        this.orderOrderDate = orderOrderDate;
    }
    
    public Timestamp getOrderStopDate()
    {
        return orderStopDate;
    }
    
    public void setOrderStopDate(Timestamp orderStopDate)
    {
        this.orderStopDate = orderStopDate;
    }
    
    public String getNote()
    {
        return note;
    }
    
    public void setNote(String note)
    {
        this.note = note;
    }
    
    public Integer getSelfDrugFlag()
    {
        return selfDrugFlag;
    }
    
    public void setSelfDrugFlag(Integer selfDrugFlag)
    {
        this.selfDrugFlag = selfDrugFlag;
    }
    
    public Integer getNutritionliquidFlag()
    {
        return nutritionliquidFlag;
    }
    
    public void setNutritionliquidFlag(Integer nutritionliquidFlag)
    {
        this.nutritionliquidFlag = nutritionliquidFlag;
    }
    
    public Integer getOrderExecuteStatus()
    {
        return orderExecuteStatus;
    }
    
    public void setOrderExecuteStatus(Integer orderExecuteStatus)
    {
        this.orderExecuteStatus = orderExecuteStatus;
    }
    
    public String getDrugPlaceCode()
    {
        return drugPlaceCode;
    }
    
    public void setDrugPlaceCode(String drugPlaceCode)
    {
        this.drugPlaceCode = drugPlaceCode;
    }
    
    public int getAction()
    {
        return action;
    }
    
    public void setAction(int action)
    {
        this.action = action;
    }
    
    public String getZxrq()
    {
        return zxrq;
    }
    
    public void setZxrq(String zxrq)
    {
        this.zxrq = zxrq;
    }
    
    public String getZxsj()
    {
        return zxsj;
    }
    
    public void setZxsj(String zxsj)
    {
        this.zxsj = zxsj;
    }
    
    @Override
    public String toString()
    {
        return "SynYzBean [id=" + id + ", orderNo=" + orderNo + ", orderGroupNo=" + orderGroupNo
            + ", orderOpenDeptCode=" + orderOpenDeptCode + ", orderOpenDeptName=" + orderOpenDeptName + ", bedNo="
            + bedNo + ", inhospNo=" + inhospNo + ", inhospIndexNo=" + inhospIndexNo + ", orderOpendrCode="
            + orderOpendrCode + ", orderOpendrName=" + orderOpendrName + ", recordDrCode=" + recordDrCode
            + ", recordDrName=" + recordDrName + ", orderFrequencyCode=" + orderFrequencyCode + ", doseWayCode="
            + doseWayCode + ", drugCode=" + drugCode + ", drugName=" + drugName + ", specifications=" + specifications
            + ", drugUseOneDosAge=" + drugUseOneDosAge + ", drugUseOneDosAgeUnit=" + drugUseOneDosAgeUnit
            + ", drugAmount=" + drugAmount + ", orderOrderDate=" + orderOrderDate + ", orderStopDate=" + orderStopDate
            + ", note=" + note + ", selfDrugFlag=" + selfDrugFlag + ", nutritionliquidFlag=" + nutritionliquidFlag
            + ", orderExecuteStatus=" + orderExecuteStatus + ", drugPlaceCode=" + drugPlaceCode + ", action=" + action
            + ", zxrq=" + zxrq + ", zxsj=" + zxsj + ", yzlx=" + yzlx + ", medicamentsPackingUnit="
            + medicamentsPackingUnit + ", patName=" + patName + ", sex=" + sex + ", birth=" + birth + ", age=" + age
            + ", ageUnit=" + ageUnit + ", avdp=" + avdp + ", dropSpeed=" + dropSpeed + ", synData=" + synData + "]";
    }
    
}
