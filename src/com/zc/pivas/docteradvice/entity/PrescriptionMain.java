package com.zc.pivas.docteradvice.entity;

import com.zc.base.common.util.DateUtil;
import com.zc.base.common.util.UniqueNumberGenerator;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 药单主表bean
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class PrescriptionMain implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public PrescriptionMain()
    {
        
    }
    
    public PrescriptionMain(DoctorAdviceMain yz, Date scrqD, Date yyrqD)
    {
        setValueByYZMain(yz, scrqD, yyrqD);
    }
    
    public void setValueByYZ(DoctorAdvice yz, Date scrqD, Date yyrqD)
    {
        
        //ydId  主键标识，自增长
        actOrderNo = "" + yz.getActOrderNo();//actOrderNo    医嘱编码
        parentNo = yz.getParentNo();//parentNo  父医嘱编码或组编码
        dybz = 1;//dybz  药单打印标志,0已打印 1未打印
        yyrq = yyrqD;
        scrq = scrqD;//scrq  药单生成日期  YYYY-MM-DD HH:MM:SS
        pidRQ = DateUtil.getDay8StrByDay(scrqD);
        //zxbc  执行批次，对应批次表的主键
        ydzxzt = yz.getYzzt();//ydzxzt    药单执行状态  0,执行  1,停止  2,撤销
        medicamentsCode = yz.getChargeCode(); //药品编码
        chargeCode = yz.getChargeCode();//chargeCode    医嘱的药品编码
        drugname = yz.getDrugname();//drugname  医嘱的药品名称
        specifications = yz.getSpecifications();//specifications    医嘱的药品规格
        dose = yz.getDose();//dose  医嘱的药品单次剂量
        doseUnit = yz.getDoseUnit();//doseUnit  医嘱的药品单次剂量单位
        quantity = yz.getQuantity();//quantity  药品数量
        bottleLabelNum = UniqueNumberGenerator.generate(13);//    药单瓶签的唯一编号,打印药单瓶签时生成
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
        zxrq = yz.getZxrq() == null ? null : yz.getZxrq();//zxrq  执行日期
        zxsj = yz.getZxsj() == null ? "" : yz.getZxsj();//zxsj  执行时间
        //pidsj = yz.getPidsj();
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
    
    public void setValueByYZMain(DoctorAdviceMain yzMain, Date scrqD, Date yyrqD)
    {
        
        //ydId  主键标识，自增长
        actOrderNo = "" + yzMain.getActOrderNo();//actOrderNo    医嘱编码
        parentNo = yzMain.getParentNo();//parentNo  父医嘱编码或组编码
        dybz = 1;//dybz  药单打印标志,0已打印 1未打印
        yyrq = yyrqD;
        /*
        try
        {// 用药日期  YYYY-MM-DD
            if (yzMain.getZxrq() != null)
            {
                yyrq = DateUtil.getDateByDayStr8(yzMain.getZxrq());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
        scrq = scrqD;//scrq  药单生成日期  YYYY-MM-DD HH:MM:SS
        pidRQ = DateUtil.getDay8StrByDay(scrq);
        //zxbc  执行批次，对应批次表的主键
        ydzxzt = yzMain.getYzzt();//ydzxzt    药单执行状态  0,执行  1,停止  2,撤销
        medicamentsCode = yzMain.getChargeCode(); //药品编码
        chargeCode = yzMain.getChargeCode();//chargeCode    医嘱的药品编码
        drugname = yzMain.getDrugname();//drugname  医嘱的药品名称
        specifications = yzMain.getSpecifications();//specifications    医嘱的药品规格
        dose = yzMain.getDose();//dose  医嘱的药品单次剂量
        doseUnit = yzMain.getDoseUnit();//doseUnit  医嘱的药品单次剂量单位
        quantity = yzMain.getQuantity();//quantity  药品数量
        bottleLabelNum = UniqueNumberGenerator.generate(13);
        ;//bottleLabelNum    药单瓶签的唯一编号,打印药单瓶签时生成
        ;//reserve1  备用字段1
        ;//reserve2  备用字段2
        ;//reserve3  备用字段3
        medicamentsPackingUnit = yzMain.getMedicamentsPackingUnit();//medicamentsPackingUnit    包装单位
        freqCode = yzMain.getFreqCode();//freqCode  医嘱的执行频率(或用法)。如: 每日一次; BID; Q12H等.
        yzshzt = yzMain.getYzshzt();//yzshzt    HIS医嘱审核状态。0：未审核 1：审核通过 2：审核不通过
        yzzdshzt = yzMain.getYzzdshzt();//yzzdshzt  医嘱自动审核状态。0：未审核 1：审核通过 2：审核不通过
        wardCode = yzMain.getWardCode();//wardCode  病区(科室)编码
        wardName = yzMain.getWardName();//wardName  病区(科室)名称
        inpatientNo = yzMain.getInpatientNo();//inpatientNo   病人就诊唯一编码
        patname = yzMain.getPatname();//patname   患者姓名
        sex = yzMain.getSex();//sex   性别：0女，1男，默认0
        bedno = yzMain.getBedno();//bedno 患者住院期间，所住床位对应的编号
        caseId = yzMain.getCaseId();//caseId    病人唯一住院号
        birthday = yzMain.getBirthday();//birthday  病人出生日期
        age = yzMain.getAge();//age   病人年龄
        ageunit = yzMain.getAgeunit();//ageunit   年龄单位，0天 1月 2年
        zxrq = yzMain.getZxrq() == null ? null : yzMain.getZxrq();//zxrq  执行日期
        zxsj = yzMain.getZxsj() == null ? "" : yzMain.getZxsj();//zxsj  执行时间
        pidsj = yzMain.getPidsj();
        yzlx = yzMain.getYzlx();
        sfysmc = yzMain.getSfysmc();
        sfyscode = yzMain.getSfyscode();
        sfrq = yzMain.getSfrq();
        doctor = yzMain.getDoctor();
        doctorName = yzMain.getDoctorName();
        avdp = yzMain.getAvdp();
        transfusion = yzMain.getTransfusion();
        dropSpeed = yzMain.getDropSpeed();
        yzResource = yzMain.getYzResource();
        startTime = yzMain.getStartTime();
        endTime = yzMain.getEndTime();
        wardNameEn = yzMain.getWardNameEn();
        bednoEn = yzMain.getBednoEn();
    }
    
    /**
     * 病区拼音 
     */
    String wardNameEn;
    
    /**
     * 床号拼音
     */
    String bednoEn;
    
    /**
     * 药单主键
     */
    Long ydMainId;
    
    /**
     * 父医嘱编号
     */
    String parentNo;
    
    /**
     * 医嘱编号
     */
    String actOrderNo;
    
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
    
    /**
     * 生成日期
     */
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
     * 药品类型
     */
    String categoryCode;
    
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
     *  包装单位
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
     * 病区编码
     */
    String wardCode;
    
    /**
     * 病区名称
     */
    String wardName;
    
    /**
     * 病人住院号
     */
    String inpatientNo;
    
    /**
     * 患者姓名
     */
    String patname;
    
    /**
     * 性别
     */
    Integer sex;
    
    /**
     * 床号
     */
    String bedno;
    
    /**
     * 病人唯一就诊号
     */
    String caseId;
    
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
     *  频次id
     */
    Long pb_id;
    
    /**
     *  频次code
     */
    String pb_num;
    
    /**
     * 频次名称
     */
    String pb_name;
    
    /**
     * 是否空批
     */
    Integer pb_isEmptyBatch;
    
    /**
     * 开始时间
     */
    String pb_startTime;
    
    /**
     * 结束时间
     */
    String pb_endTime;
    
    /**
     * 是否寄第二日医嘱
     */
    Integer pb_isSecendAdvice;
    
    /**
     * 批次颜色
     */
    String pb_color;
    
    /**
     * 是否启用
     */
    Integer pb_enabled;
    
    /**
     * 是否临批
     */
    Integer pb_is0p;
    
    /**
     * 批次时间类型
     */
    String pb_timetype;
    
    /**
     * 
     */
    String pb_reserve1;
    
    /**
     * 
     */
    String pb_reserve2;
    
    /**
     * 
     */
    String pb_reserve3;
    
    /**
     * 排序
     */
    Integer pb_orderNum;
    
    /**
     * 是否入仓
     */
    Integer rucangOKNum;
    
    /**
     * 是否出仓
     */
    Integer chucangOKNum;
    
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
     * 
     */
    Date sfrq;
    
    /**
     * 错误次数
     */
    Long chargeErrNum;
    
    /**
     * 修改时间
     */
    String chargeLastTime;
    
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
     * 开始时间
     */
    Date startTime;
    
    /**
     * 开始时间
     */
    String startTimeS;
    
    /**
     * 结束时间
     */
    Date endTime;
    
    /**
     * 结束时间
     */
    String endTimeS;
    
    /**
     * 配置费gid
     */
    Long gid;
    
    /**
     * 收费状态
     */
    String chargeStatus;
    
    /**
     * 批次名称
     */
    String pcName;
    
    /**
     * 开立时间天数
     */
    Integer startDayDelNum;
    
    /**
     * 开立时间 小时
     */
    Integer startHour;
    
    /**
     * 批次序号
     */
    Integer serialNumber;
    
    /**
     * 是否满足药物优先级
     */
    boolean prioCheck = false;
    
    Integer prioSeriNum = 999999;
    
    /**
     * 审方确认状态
     */
    Integer yzconfigStatus;//审方确认状态0待确认，1已确认，2已重排批次
    
    /**
     * 审方确认时间
     */
    Date yzconfigTime;//审方确认时间
    
    /**
     * 审方确认人ID
     */
    Integer yzconfigUid;//审方确认人ID
    
    /**
     * 审方确认人姓名
     */
    String yzconfigUname;//审方确认人姓名
    
    /**
     * 药单调整状态
     */
    Integer ydreorderStatus;//0未确认，1已确认
    
    /**
     * 自动调整批次结果
     */
    Integer ydreorderCode;//自动调整批次结果：0待处理，11批次未调整-有异常，12批次有调整-有异常，21批次未调整-无异常，22批次有调整-无异常，23用户手动调整批次，并将其置为正常，24不参与批次优化
    
    /**
     * 动调整批次时间
     */
    Date ydreorderTime;//自动调整批次时间
    
    /**
     * 动调整批次时间
     */
    String ydreorderTimeS;
    
    /**
     * 自动调整批次结果
     */
    String ydreorderMess = "";//自动调整批次结果
    
    /**
     * 批次调整前原始
     */
    Integer zxbcChangeBefore;//批次调整前原始
    
    /**
     * 批次调整前原始
     */
    String zxbcChangeBeforeS;
    
    /**
     * 批次是否已固定，不能变化
     */
    Integer zxbcIsForce = 0;//批次是否已固定，不能变化
    
    /**
     * 当前批次 容积上限
     */
    Long pcnow_maxval;//当前批次 容积上限，判断当个药单是否已超出容积上下限
    
    /**
     * 
     */
    Long pcnow_minval;//当前批次容积下限，判断当个药单是否已超出容积上下限
    
    /**
     * 药单状态
     */
    Integer pqYdzt;
    
    /**
     * 出生日期
     */
    String birthdayS;
    
    /**
     * 批次操作信息
     */
    String operationLog;
    
    /**
     * 药单数量
     */
    Integer ydCount;
    
    /**
     * 退费处理人
     */
    String tfAccount;
    
    public String getTfAccount()
    {
        return tfAccount;
    }

    public void setTfAccount(String tfAccount)
    {
        this.tfAccount = tfAccount;
    }

    public Integer getYdCount()
    {
        return ydCount;
    }

    public void setYdCount(Integer ydCount)
    {
        this.ydCount = ydCount;
    }

    public Long getYdMainId()
    {
        return ydMainId;
    }
    
    public void setYdMainId(Long ydMainId)
    {
        this.ydMainId = ydMainId;
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
    
    public String getCategoryCode()
    {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
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
    
    public Long getPb_id()
    {
        return pb_id;
    }
    
    public void setPb_id(Long pb_id)
    {
        this.pb_id = pb_id;
    }
    
    public String getPb_num()
    {
        return pb_num;
    }
    
    public void setPb_num(String pb_num)
    {
        this.pb_num = pb_num;
    }
    
    public String getPb_name()
    {
        return pb_name;
    }
    
    public void setPb_name(String pb_name)
    {
        this.pb_name = pb_name;
    }
    
    public Integer getPb_isEmptyBatch()
    {
        return pb_isEmptyBatch;
    }
    
    public void setPb_isEmptyBatch(Integer pb_isEmptyBatch)
    {
        this.pb_isEmptyBatch = pb_isEmptyBatch;
    }
    
    public String getPb_startTime()
    {
        return pb_startTime;
    }
    
    public void setPb_startTime(String pb_startTime)
    {
        this.pb_startTime = pb_startTime;
    }
    
    public String getPb_endTime()
    {
        return pb_endTime;
    }
    
    public void setPb_endTime(String pb_endTime)
    {
        this.pb_endTime = pb_endTime;
    }
    
    public Integer getPb_isSecendAdvice()
    {
        return pb_isSecendAdvice;
    }
    
    public void setPb_isSecendAdvice(Integer pb_isSecendAdvice)
    {
        this.pb_isSecendAdvice = pb_isSecendAdvice;
    }
    
    public String getPb_color()
    {
        return pb_color;
    }
    
    public void setPb_color(String pb_color)
    {
        this.pb_color = pb_color;
    }
    
    public Integer getPb_enabled()
    {
        return pb_enabled;
    }
    
    public void setPb_enabled(Integer pb_enabled)
    {
        this.pb_enabled = pb_enabled;
    }
    
    public String getPb_reserve1()
    {
        return pb_reserve1;
    }
    
    public void setPb_reserve1(String pb_reserve1)
    {
        this.pb_reserve1 = pb_reserve1;
    }
    
    public String getPb_reserve2()
    {
        return pb_reserve2;
    }
    
    public void setPb_reserve2(String pb_reserve2)
    {
        this.pb_reserve2 = pb_reserve2;
    }
    
    public String getPb_reserve3()
    {
        return pb_reserve3;
    }
    
    public void setPb_reserve3(String pb_reserve3)
    {
        this.pb_reserve3 = pb_reserve3;
    }
    
    public Integer getRucangOKNum()
    {
        return rucangOKNum;
    }
    
    public void setRucangOKNum(Integer rucangOKNum)
    {
        this.rucangOKNum = rucangOKNum;
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
    
    public Integer getChucangOKNum()
    {
        return chucangOKNum;
    }
    
    public void setChucangOKNum(Integer chucangOKNum)
    {
        this.chucangOKNum = chucangOKNum;
    }
    
    public Long getChargeErrNum()
    {
        return chargeErrNum;
    }
    
    public void setChargeErrNum(Long chargeErrNum)
    {
        this.chargeErrNum = chargeErrNum;
    }
    
    public String getChargeLastTime()
    {
        return chargeLastTime;
    }
    
    public void setChargeLastTime(String chargeLastTime)
    {
        this.chargeLastTime = chargeLastTime;
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
    
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public String getStartTimeS()
    {
        return startTimeS;
    }
    
    public void setStartTimeS(String startTimeS)
    {
        this.startTimeS = startTimeS;
    }
    
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public String getEndTimeS()
    {
        return endTimeS;
    }
    
    public void setEndTimeS(String endTimeS)
    {
        this.endTimeS = endTimeS;
    }
    
    public Integer getYzResource()
    {
        return yzResource;
    }
    
    public void setYzResource(Integer yzResource)
    {
        this.yzResource = yzResource;
    }
    
    public Long getGid()
    {
        return gid;
    }
    
    public void setGid(Long gid)
    {
        this.gid = gid;
    }
    
    public String getChargeStatus()
    {
        return chargeStatus;
    }
    
    public void setChargeStatus(String chargeStatus)
    {
        this.chargeStatus = chargeStatus;
    }
    
    public String getPcName()
    {
        return pcName;
    }
    
    public void setPcName(String pcName)
    {
        this.pcName = pcName;
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
    
    public Integer getStartDayDelNum()
    {
        return startDayDelNum;
    }
    
    public void setStartDayDelNum(Integer startDayDelNum)
    {
        this.startDayDelNum = startDayDelNum;
    }
    
    public Integer getStartHour()
    {
        return startHour;
    }
    
    public void setStartHour(Integer startHour)
    {
        this.startHour = startHour;
    }
    
    public String getScrqS()
    {
        return scrqS;
    }
    
    public void setScrqS(String scrqS)
    {
        this.scrqS = scrqS;
    }
    
    public Integer getSerialNumber()
    {
        return serialNumber;
    }
    
    public void setSerialNumber(Integer serialNumber)
    {
        this.serialNumber = serialNumber;
    }
    
    public Integer getPb_is0p()
    {
        return pb_is0p;
    }
    
    public void setPb_is0p(Integer pb_is0p)
    {
        this.pb_is0p = pb_is0p;
    }
    
    public String getPb_timetype()
    {
        return pb_timetype;
    }
    
    public void setPb_timetype(String pb_timetype)
    {
        this.pb_timetype = pb_timetype;
    }
    
    String emptyBatchTimeType;//空批上下午
    
    public String getEmptyBatchTimeType()
    {
        return emptyBatchTimeType;
    }
    
    public void setEmptyBatchTimeType(String emptyBatchTimeType)
    {
        this.emptyBatchTimeType = emptyBatchTimeType;
    }
    
    public Integer getPb_orderNum()
    {
        return pb_orderNum;
    }
    
    public void setPb_orderNum(Integer pb_orderNum)
    {
        this.pb_orderNum = pb_orderNum;
    }
    
    public Integer getYzconfigStatus()
    {
        return yzconfigStatus;
    }
    
    public void setYzconfigStatus(Integer yzconfigStatus)
    {
        this.yzconfigStatus = yzconfigStatus;
    }
    
    public Date getYzconfigTime()
    {
        return yzconfigTime;
    }
    
    public void setYzconfigTime(Date yzconfigTime)
    {
        this.yzconfigTime = yzconfigTime;
    }
    
    public Integer getYzconfigUid()
    {
        return yzconfigUid;
    }
    
    public void setYzconfigUid(Integer yzconfigUid)
    {
        this.yzconfigUid = yzconfigUid;
    }
    
    public String getYzconfigUname()
    {
        return yzconfigUname;
    }
    
    public void setYzconfigUname(String yzconfigUname)
    {
        this.yzconfigUname = yzconfigUname;
    }
    
    public Integer getYdreorderCode()
    {
        return ydreorderCode;
    }
    
    public void setYdreorderCode(Integer ydreorderCode)
    {
        this.ydreorderCode = ydreorderCode;
    }
    
    public Date getYdreorderTime()
    {
        return ydreorderTime;
    }
    
    public void setYdreorderTime(Date ydreorderTime)
    {
        this.ydreorderTime = ydreorderTime;
    }
    
    public String getYdreorderMess()
    {
        return ydreorderMess;
    }
    
    public void setYdreorderMess(String ydreorderMess)
    {
        this.ydreorderMess = ydreorderMess;
    }
    
    public Integer getZxbcChangeBefore()
    {
        return zxbcChangeBefore;
    }
    
    public void setZxbcChangeBefore(Integer zxbcChangeBefore)
    {
        this.zxbcChangeBefore = zxbcChangeBefore;
    }
    
    public Integer getZxbcIsForce()
    {
        return zxbcIsForce;
    }
    
    public void setZxbcIsForce(Integer zxbcIsForce)
    {
        this.zxbcIsForce = zxbcIsForce;
    }
    
    public boolean isPrioCheck()
    {
        return prioCheck;
    }
    
    public void setPrioCheck(boolean prioCheck)
    {
        this.prioCheck = prioCheck;
    }
    
    public String getZxbcChangeBeforeS()
    {
        return zxbcChangeBeforeS;
    }
    
    public void setZxbcChangeBeforeS(String zxbcChangeBeforeS)
    {
        this.zxbcChangeBeforeS = zxbcChangeBeforeS;
    }
    
    public Long getPcnow_maxval()
    {
        return pcnow_maxval;
    }
    
    public void setPcnow_maxval(Long pcnow_maxval)
    {
        this.pcnow_maxval = pcnow_maxval;
    }
    
    public Long getPcnow_minval()
    {
        return pcnow_minval;
    }
    
    public void setPcnow_minval(Long pcnow_minval)
    {
        this.pcnow_minval = pcnow_minval;
    }
    
    public Integer getPqYdzt()
    {
        return pqYdzt;
    }
    
    public void setPqYdzt(Integer pqYdzt)
    {
        this.pqYdzt = pqYdzt;
    }
    
    public String getBirthdayS()
    {
        return birthdayS;
    }
    
    public void setBirthdayS(String birthdayS)
    {
        this.birthdayS = birthdayS;
    }
    
    public String getWardNameEn()
    {
        return wardNameEn;
    }
    
    public void setWardNameEn(String wardNameEn)
    {
        this.wardNameEn = wardNameEn;
    }
    
    public String getBednoEn()
    {
        return bednoEn;
    }
    
    public void setBednoEn(String bednoEn)
    {
        this.bednoEn = bednoEn;
    }
    
    public Integer getYdreorderStatus()
    {
        return ydreorderStatus;
    }
    
    public void setYdreorderStatus(Integer ydreorderStatus)
    {
        this.ydreorderStatus = ydreorderStatus;
    }
    
    public String getYdreorderTimeS()
    {
        return ydreorderTimeS;
    }
    
    public void setYdreorderTimeS(String ydreorderTimeS)
    {
        this.ydreorderTimeS = ydreorderTimeS;
    }
    
    public Integer getPrioSeriNum()
    {
        return prioSeriNum;
    }
    
    public void setPrioSeriNum(Integer prioSeriNum)
    {
        this.prioSeriNum = prioSeriNum;
    }

    /**
     * @return 返回 operationLog
     */
    public String getOperationLog()
    {
        return operationLog;
    }

    /**
     * @param
     */
    public void setOperationLog(String operationLog)
    {
        this.operationLog = operationLog;
    }

}
