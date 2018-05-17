package com.zc.pivas.docteradvice.entity;

import com.zc.base.common.util.DateUtil;
import com.zc.pivas.excuteRecord.bean.ExcuteRecordBean;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 药单实体bean
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class Prescription implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public Prescription()
    {
        
    }
    
    public Prescription(DoctorAdvice yz, Date scrqD, Date yyrqD)
    {
        setValueByYZ(yz, scrqD, yyrqD);
    }
    
    public Prescription(DoctorAdvice yz, Date scrqD, Date yyrqD, ExcuteRecordBean excuteRecordBean)
    {
        //ydId  主键标识，自增长
        actOrderNo = yz.getActOrderNo();//actOrderNo    医嘱编码
        parentNo = yz.getParentNo();//parentNo  父医嘱编码或组编码
        dybz = 1;//dybz  药单打印标志,0已打印 1未打印
        yyrq = yyrqD;
        scrq = scrqD;//scrq  药单生成日期  YYYY-MM-DD HH:MM:SS
        pidRQ = DateUtil.getDay8StrByDay(scrq);
        //zxbc  执行批次，对应批次表的主键
        ydzxzt = yz.getYzzt();//ydzxzt    药单执行状态  0,执行  1,停止  2,撤销
        medicamentsCode = excuteRecordBean.getDrugCode(); //药品编码
        chargeCode = excuteRecordBean.getDrugCode();//chargeCode    医嘱的药品编码
        drugname = excuteRecordBean.getDrugName();//drugname  医嘱的药品名称
        specifications = yz.getSpecifications();//specifications    医嘱的药品规格
        dose = yz.getDose();//dose  医嘱的药品单次剂量
        doseUnit = yz.getDoseUnit();//doseUnit  医嘱的药品单次剂量单位
        quantity = excuteRecordBean.getQuantity()//quantity  药品数量
        ;//bottleLabelNum    药单瓶签的唯一编号,打印药单瓶签时生成
        ;//reserve1  备用字段1
        ;//reserve2  备用字段2
        ;//reserve3  备用字段3
        medicamentsPackingUnit = yz.getMedicamentsPackingUnit();//medicamentsPackingUnit    包装单位
        freqCode = excuteRecordBean.getDrugFreq();//freqCode  医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.
        yzshzt = yz.getYzshzt();//yzshzt    HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过
        yzzdshzt = yz.getYzzdshzt();//yzzdshzt  医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过
        wardCode = yz.getWardCode();//wardCode  病区(科室)编码
        wardName = yz.getWardName();//wardName  病区(科室)名称
        inpatientNo = yz.getInpatientNo();//inpatientNo   病人就诊唯一编码
        patname = StringUtils.isNotBlank(excuteRecordBean.getPatName())?excuteRecordBean.getPatName():yz.getPatname();//patname   患者姓名
        sex = yz.getSex();//sex   性别：0女，1男，默认0
        bedno = StringUtils.isNotBlank(excuteRecordBean.getBedNo())?excuteRecordBean.getBedNo():yz.getBedno();//bedno 患者住院期间，所住床位对应的编号
        caseId = yz.getCaseId();//caseId    病人唯一住院号
        birthday = yz.getBirthday();//birthday  病人出生日期
        age = yz.getAge();//age   病人年龄
        ageunit = yz.getAgeunit();//ageunit   年龄单位，0天 1月 2年
        zxrq = yz.getZxrq() == null ? "" : yz.getZxrq();//zxrq  执行日期
        zxsj = yz.getZxsj() == null ? "" : yz.getZxsj();//zxsj  执行时间
        pidsj = yz.getPidsj();
        yzlx = yz.getYzlx();
        sfysmc = yz.getSfysmc();
        sfyscode = yz.getSfyscode();
        sfrq = yz.getSfrq();
        doctor = yz.getDoctor();
        doctorName = yz.getDoctorName();
        avdp = yz.getAvdp();
        transfusion = yz.getTransfusion();
        dropSpeed = yz.getDropSpeed();
        yzResource = yz.getYzResource();
    }

    public void setValueByYZ(DoctorAdvice yz, Date scrqD, Date yyrqD)
    {
        
        //ydId  主键标识，自增长
        actOrderNo = yz.getActOrderNo();//actOrderNo    医嘱编码
        parentNo = yz.getParentNo();//parentNo  父医嘱编码或组编码
        dybz = 1;//dybz  药单打印标志,0已打印 1未打印
        yyrq = yyrqD;
        /*
        try
        {
            if (yz.getZxrq() != null)
            {
                yyrq = DateUtil.getDateByDayStr8(yz.getZxrq());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
        scrq = scrqD;//scrq  药单生成日期  YYYY-MM-DD HH:MM:SS
        pidRQ = DateUtil.getDay8StrByDay(scrq);
        //zxbc  执行批次，对应批次表的主键
        ydzxzt = yz.getYzzt();//ydzxzt    药单执行状态  0,执行  1,停止  2,撤销
        medicamentsCode = yz.getChargeCode(); //药品编码
        chargeCode = yz.getChargeCode();//chargeCode    医嘱的药品编码
        drugname = yz.getMedicamentsName();//drugname  医嘱的药品名称
        specifications = yz.getSpecifications();//specifications    医嘱的药品规格
        dose = yz.getDose();//dose  医嘱的药品单次剂量
        doseUnit = yz.getDoseUnit();//doseUnit  医嘱的药品单次剂量单位
        quantity = yz.getQuantity();//quantity  药品数量
        ;//bottleLabelNum    药单瓶签的唯一编号,打印药单瓶签时生成
        ;//reserve1  备用字段1
        ;//reserve2  备用字段2
        ;//reserve3  备用字段3
        medicamentsPackingUnit = yz.getMedicamentsPackingUnit();//medicamentsPackingUnit    包装单位
        freqCode = yz.getFreqCode();//freqCode  医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.
        yzshzt = yz.getYzshzt();//yzshzt    HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过
        yzzdshzt = yz.getYzzdshzt();//yzzdshzt  医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过
        wardCode = yz.getWardCode();//wardCode  病区(科室)编码
        wardName = yz.getWardName();//wardName  病区(科室)名称
        inpatientNo = yz.getInpatientNo();//inpatientNo   病人就诊唯一编码
        patname = yz.getPatname();//patname   患者姓名
        sex = yz.getSex();//sex   性别：0女，1男，默认0
        bedno = yz.getBedno();//bedno 患者住院期间，所住床位对应的编号
        caseId = yz.getCaseId();//caseId    病人唯一住院号
        birthday = yz.getBirthday();//birthday  病人出生日期
        age = yz.getAge();//age   病人年龄
        ageunit = yz.getAgeunit();//ageunit   年龄单位，0天 1月 2年
        zxrq = yz.getZxrq() == null ? "" : yz.getZxrq();//zxrq  执行日期
        zxsj = yz.getZxsj() == null ? "" : yz.getZxsj();//zxsj  执行时间
        pidsj = yz.getPidsj();
        yzlx = yz.getYzlx();
        sfysmc = yz.getSfysmc();
        sfyscode = yz.getSfyscode();
        sfrq = yz.getSfrq();
        doctor = yz.getDoctor();
        doctorName = yz.getDoctorName();
        avdp = yz.getAvdp();
        transfusion = yz.getTransfusion();
        dropSpeed = yz.getDropSpeed();
        yzResource = yz.getYzResource();
    }
    
    /**
     * 药单id
     */
    Long ydId;
    
    /**
     * 父医嘱编号
     */
    String parentNo;
    
    /**
     * 医嘱编号
     */
    String actOrderNo;
    
    /**
     * 病区编码
     */
    String wardCode;
    
    /**
     * 病区名称
     */
    String wardName;
    
    /**
     * 床号
     */
    String bedno;
    
    /**
     * 病人住院号
     */
    String inpatientNo;
    
    /**
     * 病人唯一就诊号
     */
    String caseId;
    
    /**
     * 打印标识
     */
    Integer dybz;
    
    /**
     * 用药日期
     */
    Date yyrq;
    
    /**
     * 用药日期
     */
    String yyrqS;
    
    /**
     * 父医嘱+日期
     */
    String pidRQ;
    
    /**
     * 生成日期
     */
    Date scrq;
    
    String scrqS;
    
    /**
     * 执行批次
     */
    Integer zxbc;
    
    /**
     * 药单执行状态
     */
    Integer ydzxzt;
    
    /**
     * 药品code
     */
    String medicamentsCode;
    
    /**
     * 药品编码
     */
    String chargeCode;
    
    /**
     * 药品名称
     */
    String drugname;
    
    /**
     * 药品规格
     */
    String specifications;
    
    /**
     * 药品单次剂量
     */
    String dose;
    
    /**
     * 药品单次剂量单位
     */
    String doseUnit;
    
    /**
     * 药品数量
     */
    String quantity;
    
    /**
     * 药单瓶签
     */
    String bottleLabelNum;
    
    /**
     * 备用1
     */
    String reserve1;
    
    /**
     * 备用2
     */
    String reserve2;
    
    /**
     * 备用3
     */
    String reserve3;
    
    /**
     * 包装单位
     */
    String medicamentsPackingUnit;
    
    /**
     * 医嘱的执行频率
     */
    String freqCode;
    
    /**
     * 医嘱审核状态
     */
    Integer yzshzt;
    
    /**
     * 医嘱自动审核状态
     */
    Integer yzzdshzt;
    
    /**
     * 患者姓名
     */
    String patname;
    
    /**
     * 性别
     */
    Integer sex;
    
    /**
     * 出生日期
     */
    Date birthday;
    
    /**
     * 年龄
     */
    String age;
    
    /**
     * 年龄单位
     */
    Integer ageunit;
    
    /**
     * 执行日期
     */
    String zxrq;
    
    /**
     * 执行时间
     */
    String zxsj;
    
    /**
     * 父医嘱+执行时间
     */
    String pidsj;
    
    /**
     * 医嘱类型
     */
    Integer yzlx;
    
    /**
     * 审方人
     */
    String sfysmc;
    
    /**
     * 审方人
     */
    String sfyscode;
    
    /**
     * 审方日期
     */
    Date sfrq;
    
    /**
     * 父医嘱+执行批次
     */
    String pidrqzxbc;
    
    /**
     * 开嘱医生的工号
     */
    String doctor;
    
    /**
     * 开嘱医生姓名
     */
    String doctorName;
    
    /**
     * 病人体重
     */
    String avdp;
    
    /**
     * 输液量ML
     */
    String transfusion;
    
    /**
     * 医嘱滴速
     */
    String dropSpeed;
    
    /**
     * 临时医嘱来源
     */
    Integer yzResource;
    
    /**
     * 批次序号
     */
    Integer serialNumber;
    
    /**
     * 分类名称
     */
    String categoryName;
    
    /**
     * 规格
     */
    String specifications2;
    
    public Long getYdId()
    {
        return ydId;
    }
    
    public void setYdId(Long ydId)
    {
        this.ydId = ydId;
    }
    
    public String getActOrderNo()
    {
        return actOrderNo;
    }
    
    public void setActOrderNo(String actOrderNo)
    {
        this.actOrderNo = actOrderNo;
    }
    
    public String getParentNo()
    {
        return parentNo;
    }
    
    public void setParentNo(String parentNo)
    {
        this.parentNo = parentNo;
    }
    
    public Integer getDybz()
    {
        return dybz;
    }
    
    public void setDybz(Integer dybz)
    {
        this.dybz = dybz;
    }
    
    public Date getYyrq()
    {
        return yyrq;
    }
    
    public void setYyrq(Date yyrq)
    {
        this.yyrq = yyrq;
    }
    
    public Date getScrq()
    {
        return scrq;
    }
    
    public void setScrq(Date scrq)
    {
        this.scrq = scrq;
    }
    
    public String getScrqS()
    {
        return scrqS;
    }

    public void setScrqS(String scrqS)
    {
        this.scrqS = scrqS;
    }

    public Integer getZxbc()
    {
        return zxbc;
    }
    
    public void setZxbc(Integer zxbc)
    {
        this.zxbc = zxbc;
    }
    
    public Integer getYdzxzt()
    {
        return ydzxzt;
    }
    
    public void setYdzxzt(Integer ydzxzt)
    {
        this.ydzxzt = ydzxzt;
    }
    
    public String getMedicamentsCode()
    {
        return medicamentsCode;
    }
    
    public void setMedicamentsCode(String medicamentsCode)
    {
        this.medicamentsCode = medicamentsCode;
    }
    
    public String getChargeCode()
    {
        return chargeCode;
    }
    
    public void setChargeCode(String chargeCode)
    {
        this.chargeCode = chargeCode;
    }
    
    public String getDrugname()
    {
        return drugname;
    }
    
    public void setDrugname(String drugname)
    {
        this.drugname = drugname;
    }
    
    public String getSpecifications()
    {
        return specifications;
    }
    
    public void setSpecifications(String specifications)
    {
        this.specifications = specifications;
    }
    
    public String getDose()
    {
        return dose;
    }
    
    public void setDose(String dose)
    {
        this.dose = dose;
    }
    
    public String getDoseUnit()
    {
        return doseUnit;
    }
    
    public void setDoseUnit(String doseUnit)
    {
        this.doseUnit = doseUnit;
    }
    
    public String getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    
    public String getBottleLabelNum()
    {
        return bottleLabelNum;
    }
    
    public void setBottleLabelNum(String bottleLabelNum)
    {
        this.bottleLabelNum = bottleLabelNum;
    }
    
    public String getReserve1()
    {
        return reserve1;
    }
    
    public void setReserve1(String reserve1)
    {
        this.reserve1 = reserve1;
    }
    
    public String getReserve2()
    {
        return reserve2;
    }
    
    public void setReserve2(String reserve2)
    {
        this.reserve2 = reserve2;
    }
    
    public String getReserve3()
    {
        return reserve3;
    }
    
    public void setReserve3(String reserve3)
    {
        this.reserve3 = reserve3;
    }
    
    public String getMedicamentsPackingUnit()
    {
        return medicamentsPackingUnit;
    }
    
    public void setMedicamentsPackingUnit(String medicamentsPackingUnit)
    {
        this.medicamentsPackingUnit = medicamentsPackingUnit;
    }
    
    public String getFreqCode()
    {
        return freqCode;
    }
    
    public void setFreqCode(String freqCode)
    {
        this.freqCode = freqCode;
    }
    
    public Integer getYzshzt()
    {
        return yzshzt;
    }
    
    public void setYzshzt(Integer yzshzt)
    {
        this.yzshzt = yzshzt;
    }
    
    public Integer getYzzdshzt()
    {
        return yzzdshzt;
    }
    
    public void setYzzdshzt(Integer yzzdshzt)
    {
        this.yzzdshzt = yzzdshzt;
    }
    
    public String getWardCode()
    {
        return wardCode;
    }
    
    public void setWardCode(String wardCode)
    {
        this.wardCode = wardCode;
    }
    
    public String getWardName()
    {
        return wardName;
    }
    
    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }
    
    public String getInpatientNo()
    {
        return inpatientNo;
    }
    
    public void setInpatientNo(String inpatientNo)
    {
        this.inpatientNo = inpatientNo;
    }
    
    public String getPatname()
    {
        return patname;
    }
    
    public void setPatname(String patname)
    {
        this.patname = patname;
    }
    
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    public String getBedno()
    {
        return bedno;
    }
    
    public void setBedno(String bedno)
    {
        this.bedno = bedno;
    }
    
    public String getCaseId()
    {
        return caseId;
    }
    
    public void setCaseId(String caseId)
    {
        this.caseId = caseId;
    }
    
    public Date getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public Integer getAgeunit()
    {
        return ageunit;
    }
    
    public void setAgeunit(Integer ageunit)
    {
        this.ageunit = ageunit;
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
    
    public String getPidsj()
    {
        return pidsj;
    }
    
    public void setPidsj(String pidsj)
    {
        this.pidsj = pidsj;
    }
    
    public Integer getYzlx()
    {
        return yzlx;
    }
    
    public void setYzlx(Integer yzlx)
    {
        this.yzlx = yzlx;
    }
    
    public String getSfysmc()
    {
        return sfysmc;
    }
    
    public void setSfysmc(String sfysmc)
    {
        this.sfysmc = sfysmc;
    }
    
    public String getSfyscode()
    {
        return sfyscode;
    }
    
    public void setSfyscode(String sfyscode)
    {
        this.sfyscode = sfyscode;
    }
    
    public Date getSfrq()
    {
        return sfrq;
    }
    
    public void setSfrq(Date sfrq)
    {
        this.sfrq = sfrq;
    }
    
    public String getPidrqzxbc()
    {
        return pidrqzxbc;
    }
    
    public void setPidrqzxbc(String pidrqzxbc)
    {
        this.pidrqzxbc = pidrqzxbc;
    }
    
    public String getDoctor()
    {
        return doctor;
    }
    
    public void setDoctor(String doctor)
    {
        this.doctor = doctor;
    }
    
    public String getDoctorName()
    {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }
    
    public String getAvdp()
    {
        return avdp;
    }
    
    public void setAvdp(String avdp)
    {
        this.avdp = avdp;
    }
    
    public String getTransfusion()
    {
        return transfusion;
    }
    
    public void setTransfusion(String transfusion)
    {
        this.transfusion = transfusion;
    }
    
    public String getDropSpeed()
    {
        return dropSpeed;
    }
    
    public void setDropSpeed(String dropSpeed)
    {
        this.dropSpeed = dropSpeed;
    }
    
    public Integer getYzResource()
    {
        return yzResource;
    }
    
    public void setYzResource(Integer yzResource)
    {
        this.yzResource = yzResource;
    }
    
    public String getPidRQ()
    {
        return pidRQ;
    }
    
    public void setPidRQ(String pidRQ)
    {
        this.pidRQ = pidRQ;
    }
    
    public String getYyrqS()
    {
        return yyrqS;
    }
    
    public void setYyrqS(String yyrqS)
    {
        this.yyrqS = yyrqS;
    }
    
    public Integer getSerialNumber()
    {
        return serialNumber;
    }
    
    public void setSerialNumber(Integer serialNumber)
    {
        this.serialNumber = serialNumber;
    }
    
    public String getCategoryName()
    {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    public String getSpecifications2()
    {
        return specifications2;
    }
    
    public void setSpecifications2(String specifications2)
    {
        this.specifications2 = specifications2;
    }
    
}
