package com.zc.pivas.docteradvice.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 药单和瓶签数据封装类
 * 
 *
 * @author  cacabin
 * @version  0.1
 */
public class BLabelWithPrescription implements Serializable
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用药日期相对今日的天数
     */
    Integer yyrqDelToday;

    /**
     * 生成日期相对今日的天数
     */
    Integer scrqDelToday;

    /**
     * 批次名称2
     */
    String pbname2;//扫描时用的，前台传 上午，下午，后台根据 上午，下午查询

    /**
     * 药单主表id
     */
    Long yd_ydMainId;

    /**
     * 医嘱主键
     */
    String yd_actNrderNo;

    /**
     * 父医嘱编号
     */
    String yd_parentNo;

    /**
     * 打印标志
     */
    Integer yd_dybz;

    /**
     * 用药日期
     */
    Date yd_yyrq;

    /**
     * 生成日期
     */
    Date yd_scrq;

    /**
     * 执行批次
     */
    Integer yd_zxbc;

    /**
     * 药单执行状态
     */
    Integer yd_ydzxzt;

    /**
     * 药品code
     */
    String yd_medicamentsCode;

    /**
     * 费用code
     */
    String yd_chargeCode;

    /**
     * 药品名称
     */
    String yd_drugname;

    /**
     * 药品规格
     */
    String yd_specifications;

    /**
     * 单次剂量
     */
    String yd_dose;

    /**
     * 单次剂量单位
     */
    String yd_doseUnit;

    /**
     * 药品数量
     */
    String yd_quantity;

    /**
     * 瓶签号
     */
    String yd_bottleLabelNum;

    /**
     * 备用1
     */
    String yd_reserve1;

    /**
     * 备用2
     */
    String yd_reserve2;

    /**
     * 备用3
     */
    String yd_reserve3;

    /**
     * 包装单位
     */
    String yd_medicamentsPackingUnit;

    /**
     * 频次code
     */
    String yd_freq_code;

    /**
     * 医嘱审方状态
     */
    Integer yd_yzshzt;

    /**
     * 自动审核状态
     */
    Integer yd_yzzdshzt;

    /**
     * 病区编码
     */
    String yd_wardCode;

    /**
     * 病区名称
     */
    String yd_wardname;

    /**
     * 病人就诊唯一编码
     */
    String yd_inpatientNo;

    /**
     * 患者姓名
     */
    String yd_patname;

    /**
     * 性别
     */
    Integer yd_sex;

    /**
     * 床位对应的编号
     */
    String yd_bedno;

    /**
     * 病人唯一住院号
     */
    String yd_caseId;

    /**
     * 病人出生日期
     */
    Date yd_birthday;

    /**
     * 病人年龄
     */
    String yd_age;

    /**
     * 年龄单位，0天 1月 2年
     */
    Integer yd_ageunit;

    /**
     * 执行日期
     */
    String yd_zxrq;

    /**
     * 执行时间
     */
    String yd_zxsj;

    /**
     * 父医嘱编号+时间
     */
    String yd_pidsj;

    /**
     * 医嘱类型
     */
    Integer yd_yzlx;

    /**
     * 审核药师名名称
     */
    String yd_sfysmc;

    /**
     * 审核药师code
     */
    String yd_sfyscode;

    /**
     * 审方日期
     */
    Date yd_sfrq;

    /**
     * 父医嘱编号+执行批次
     */
    String yd_pidrqzxbc;

    /**
     * 开嘱医生的工号
     */
    String yd_doctor;

    /**
     * 开嘱医生姓名
     */
    String yd_doctorName;

    /**
     * 病人体重
     */
    String yd_avdp;

    /**
     * 输液量ML
     */
    String yd_transfusion;

    /**
     * 医嘱滴速
     */
    String yd_dropspeed;

    /**
     * 临时医嘱来源
     */
    Integer yd_yzresource;

    /**
     * 医嘱开始时间
     */
    Date yd_startTime;

    /**
     * 医嘱停止时间
     */
    Date yd_endTime;

    /**
     * 父医嘱编号
     */
    String pq_parentNo;

    /**
     * 执行批次
     */
    Integer pq_zxbc;

    /**
     * 执行日期
     */
    Date pq_yyrq;

    /**
     * 药单状态
     */
    Integer pq_ydzt;

    /**
     * 瓶签
     */
    String pq_ydpq;

    /**
     * 上次瓶签
     */
    String pq_ydpqLast;

    /**
     * 上次批次
     */
    Integer pq_zxbcLast;

    /**
     * 病区名称
     */
    String pq_deptname;

    /**
     * 药品分类
     */
    String pq_categoryCodeList;

    /**
     * 住院流水号
     */
    String pq_inhospno;

    /**
     * 患者姓名
     */
    String pq_patname;

    /**
     * 性别
     */
    Integer pq_sex;

    /**
     * 病人唯一住院号
     */
    String pq_caseId;

    /**
     * 病人出生日期
     */
    Date pq_birthday;

    /**
     * 病人年龄
     */
    String pq_age;

    /**
     * 年龄单位
     */
    Integer pq_ageunit;

    /**
     * 病人体重
     */
    String pq_avdp;

    /**
     * 医嘱审核药师名
     */
    String pq_sfysmc;

    /**
     * 住床位对应的编号
     */
    String pq_bedno;

    /**
     * 执行日期
     */
    String pq_zxrq;

    /**
     * 执行时间
     */
    String pq_zxsj;

    /**
     * 父医嘱编号+时间
     */
    String pq_pidsj;

    /**
     * 是否重复打印
     */
    Integer pq_isrepeat;

    /**
     * 病区编码
     */
    String pq_deptcode;

    /**
     * 父医嘱编号+执行批次
     */
    String pq_pidrqzxbc;

    /**
     * 医嘱类型
     */
    Integer pq_yzlx;

    /**
     * 打印时间
     */
    Date pq_printTime;

    /**
     * 打印时间
     */
    String pq_printTimeS;

    /**
     * 频次id
     */
    Long pb_id;

    /**
     * 用药频次编码
     */
    String pb_num;

    /**
     * 用药频次名称
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
     * 颜色
     */
    String pb_color;

    /**
     * 是否启用
     */
    Integer pb_enabled;

    /**
     * 备用1
     */
    String pb_reserve1;

    /**
     * 备用2
     */
    String pb_reserve2;

    /**
     * 备用3
     */
    String pb_reserve3;

    /**
     * 是否临批
     */
    Integer pb_is0p;
    
    public String getPbname2()
    {
        return pbname2;
    }
    
    public void setPbname2(String pbname2)
    {
        this.pbname2 = pbname2;
    }
    
    public Integer getYyrqDelToday()
    {
        return yyrqDelToday;
    }
    
    public void setYyrqDelToday(Integer yyrqDelToday)
    {
        this.yyrqDelToday = yyrqDelToday;
    }
    
    public Long getYd_ydMainId()
    {
        return yd_ydMainId;
    }
    
    public void setYd_ydMainId(Long yd_ydMainId)
    {
        this.yd_ydMainId = yd_ydMainId;
    }
    
    public String getYd_actNrderNo()
    {
        return yd_actNrderNo;
    }
    
    public void setYd_actNrderNo(String yd_actNrderNo)
    {
        this.yd_actNrderNo = yd_actNrderNo;
    }
    
    public String getYd_parentNo()
    {
        return yd_parentNo;
    }
    
    public void setYd_parentNo(String yd_parentNo)
    {
        this.yd_parentNo = yd_parentNo;
    }
    
    public Integer getYd_dybz()
    {
        return yd_dybz;
    }
    
    public void setYd_dybz(Integer yd_dybz)
    {
        this.yd_dybz = yd_dybz;
    }
    
    public Date getYd_yyrq()
    {
        return yd_yyrq;
    }
    
    public void setYd_yyrq(Date yd_yyrq)
    {
        this.yd_yyrq = yd_yyrq;
    }
    
    public Date getYd_scrq()
    {
        return yd_scrq;
    }
    
    public void setYd_scrq(Date yd_scrq)
    {
        this.yd_scrq = yd_scrq;
    }
    
    public Integer getYd_zxbc()
    {
        return yd_zxbc;
    }
    
    public void setYd_zxbc(Integer yd_zxbc)
    {
        this.yd_zxbc = yd_zxbc;
    }
    
    public Integer getYd_ydzxzt()
    {
        return yd_ydzxzt;
    }
    
    public void setYd_ydzxzt(Integer yd_ydzxzt)
    {
        this.yd_ydzxzt = yd_ydzxzt;
    }
    
    public String getYd_medicamentsCode()
    {
        return yd_medicamentsCode;
    }
    
    public void setYd_medicamentsCode(String yd_medicamentsCode)
    {
        this.yd_medicamentsCode = yd_medicamentsCode;
    }
    
    public String getYd_chargeCode()
    {
        return yd_chargeCode;
    }
    
    public void setYd_chargeCode(String yd_chargeCode)
    {
        this.yd_chargeCode = yd_chargeCode;
    }
    
    public String getYd_drugname()
    {
        return yd_drugname;
    }
    
    public void setYd_drugname(String yd_drugname)
    {
        this.yd_drugname = yd_drugname;
    }
    
    public String getYd_specifications()
    {
        return yd_specifications;
    }
    
    public void setYd_specifications(String yd_specifications)
    {
        this.yd_specifications = yd_specifications;
    }
    
    public String getYd_dose()
    {
        return yd_dose;
    }
    
    public void setYd_dose(String yd_dose)
    {
        this.yd_dose = yd_dose;
    }
    
    public String getYd_doseUnit()
    {
        return yd_doseUnit;
    }
    
    public void setYd_doseUnit(String yd_doseUnit)
    {
        this.yd_doseUnit = yd_doseUnit;
    }
    
    public String getYd_quantity()
    {
        return yd_quantity;
    }
    
    public void setYd_quantity(String yd_quantity)
    {
        this.yd_quantity = yd_quantity;
    }
    
    public String getYd_bottleLabelNum()
    {
        return yd_bottleLabelNum;
    }
    
    public void setYd_bottleLabelNum(String yd_bottleLabelNum)
    {
        this.yd_bottleLabelNum = yd_bottleLabelNum;
    }
    
    public String getYd_reserve1()
    {
        return yd_reserve1;
    }
    
    public void setYd_reserve1(String yd_reserve1)
    {
        this.yd_reserve1 = yd_reserve1;
    }
    
    public String getYd_reserve2()
    {
        return yd_reserve2;
    }
    
    public void setYd_reserve2(String yd_reserve2)
    {
        this.yd_reserve2 = yd_reserve2;
    }
    
    public String getYd_reserve3()
    {
        return yd_reserve3;
    }
    
    public void setYd_reserve3(String yd_reserve3)
    {
        this.yd_reserve3 = yd_reserve3;
    }
    
    public String getYd_medicamentsPackingUnit()
    {
        return yd_medicamentsPackingUnit;
    }
    
    public void setYd_medicamentsPackingUnit(String yd_medicamentsPackingUnit)
    {
        this.yd_medicamentsPackingUnit = yd_medicamentsPackingUnit;
    }
    
    public String getYd_freq_code()
    {
        return yd_freq_code;
    }
    
    public void setYd_freq_code(String yd_freq_code)
    {
        this.yd_freq_code = yd_freq_code;
    }
    
    public Integer getYd_yzshzt()
    {
        return yd_yzshzt;
    }
    
    public void setYd_yzshzt(Integer yd_yzshzt)
    {
        this.yd_yzshzt = yd_yzshzt;
    }
    
    public Integer getYd_yzzdshzt()
    {
        return yd_yzzdshzt;
    }
    
    public void setYd_yzzdshzt(Integer yd_yzzdshzt)
    {
        this.yd_yzzdshzt = yd_yzzdshzt;
    }
    
    public String getYd_wardCode()
    {
        return yd_wardCode;
    }
    
    public void setYd_wardCode(String yd_wardCode)
    {
        this.yd_wardCode = yd_wardCode;
    }
    
    public String getYd_wardname()
    {
        return yd_wardname;
    }
    
    public void setYd_wardname(String yd_wardname)
    {
        this.yd_wardname = yd_wardname;
    }
    
    public String getYd_inpatientNo()
    {
        return yd_inpatientNo;
    }
    
    public void setYd_inpatientNo(String yd_inpatientNo)
    {
        this.yd_inpatientNo = yd_inpatientNo;
    }
    
    public String getYd_patname()
    {
        return yd_patname;
    }
    
    public void setYd_patname(String yd_patname)
    {
        this.yd_patname = yd_patname;
    }
    
    public Integer getYd_sex()
    {
        return yd_sex;
    }
    
    public void setYd_sex(Integer yd_sex)
    {
        this.yd_sex = yd_sex;
    }
    
    public String getYd_bedno()
    {
        return yd_bedno;
    }
    
    public void setYd_bedno(String yd_bedno)
    {
        this.yd_bedno = yd_bedno;
    }
    
    public String getYd_caseId()
    {
        return yd_caseId;
    }
    
    public void setYd_caseId(String yd_caseId)
    {
        this.yd_caseId = yd_caseId;
    }
    
    public Date getYd_birthday()
    {
        return yd_birthday;
    }
    
    public void setYd_birthday(Date yd_birthday)
    {
        this.yd_birthday = yd_birthday;
    }
    
    public String getYd_age()
    {
        return yd_age;
    }
    
    public void setYd_age(String yd_age)
    {
        this.yd_age = yd_age;
    }
    
    public Integer getYd_ageunit()
    {
        return yd_ageunit;
    }
    
    public void setYd_ageunit(Integer yd_ageunit)
    {
        this.yd_ageunit = yd_ageunit;
    }
    
    public String getYd_zxrq()
    {
        return yd_zxrq;
    }
    
    public void setYd_zxrq(String yd_zxrq)
    {
        this.yd_zxrq = yd_zxrq;
    }
    
    public String getYd_zxsj()
    {
        return yd_zxsj;
    }
    
    public void setYd_zxsj(String yd_zxsj)
    {
        this.yd_zxsj = yd_zxsj;
    }
    
    public String getYd_pidsj()
    {
        return yd_pidsj;
    }
    
    public void setYd_pidsj(String yd_pidsj)
    {
        this.yd_pidsj = yd_pidsj;
    }
    
    public Integer getYd_yzlx()
    {
        return yd_yzlx;
    }
    
    public void setYd_yzlx(Integer yd_yzlx)
    {
        this.yd_yzlx = yd_yzlx;
    }
    
    public String getYd_sfysmc()
    {
        return yd_sfysmc;
    }
    
    public void setYd_sfysmc(String yd_sfysmc)
    {
        this.yd_sfysmc = yd_sfysmc;
    }
    
    public String getYd_sfyscode()
    {
        return yd_sfyscode;
    }
    
    public void setYd_sfyscode(String yd_sfyscode)
    {
        this.yd_sfyscode = yd_sfyscode;
    }
    
    public Date getYd_sfrq()
    {
        return yd_sfrq;
    }
    
    public void setYd_sfrq(Date yd_sfrq)
    {
        this.yd_sfrq = yd_sfrq;
    }
    
    public String getYd_pidrqzxbc()
    {
        return yd_pidrqzxbc;
    }
    
    public void setYd_pidrqzxbc(String yd_pidrqzxbc)
    {
        this.yd_pidrqzxbc = yd_pidrqzxbc;
    }
    
    public String getYd_doctor()
    {
        return yd_doctor;
    }
    
    public void setYd_doctor(String yd_doctor)
    {
        this.yd_doctor = yd_doctor;
    }
    
    public String getYd_doctorName()
    {
        return yd_doctorName;
    }
    
    public void setYd_doctorName(String yd_doctorName)
    {
        this.yd_doctorName = yd_doctorName;
    }
    
    public String getYd_avdp()
    {
        return yd_avdp;
    }
    
    public void setYd_avdp(String yd_avdp)
    {
        this.yd_avdp = yd_avdp;
    }
    
    public String getYd_transfusion()
    {
        return yd_transfusion;
    }
    
    public void setYd_transfusion(String yd_transfusion)
    {
        this.yd_transfusion = yd_transfusion;
    }
    
    public String getYd_dropspeed()
    {
        return yd_dropspeed;
    }
    
    public void setYd_dropspeed(String yd_dropspeed)
    {
        this.yd_dropspeed = yd_dropspeed;
    }
    
    public Integer getYd_yzresource()
    {
        return yd_yzresource;
    }
    
    public void setYd_yzresource(Integer yd_yzresource)
    {
        this.yd_yzresource = yd_yzresource;
    }
    
    public Date getYd_startTime()
    {
        return yd_startTime;
    }
    
    public void setYd_startTime(Date yd_startTime)
    {
        this.yd_startTime = yd_startTime;
    }
    
    public Date getYd_endTime()
    {
        return yd_endTime;
    }
    
    public void setYd_endTime(Date yd_endTime)
    {
        this.yd_endTime = yd_endTime;
    }
    
    public String getPq_parentNo()
    {
        return pq_parentNo;
    }
    
    public void setPq_parentNo(String pq_parentNo)
    {
        this.pq_parentNo = pq_parentNo;
    }
    
    public Integer getPq_zxbc()
    {
        return pq_zxbc;
    }
    
    public void setPq_zxbc(Integer pq_zxbc)
    {
        this.pq_zxbc = pq_zxbc;
    }
    
    public Date getPq_yyrq()
    {
        return pq_yyrq;
    }
    
    public void setPq_yyrq(Date pq_yyrq)
    {
        this.pq_yyrq = pq_yyrq;
    }
    
    public Integer getPq_ydzt()
    {
        return pq_ydzt;
    }
    
    public void setPq_ydzt(Integer pq_ydzt)
    {
        this.pq_ydzt = pq_ydzt;
    }
    
    public String getPq_ydpq()
    {
        return pq_ydpq;
    }
    
    public void setPq_ydpq(String pq_ydpq)
    {
        this.pq_ydpq = pq_ydpq;
    }
    
    public String getPq_deptname()
    {
        return pq_deptname;
    }
    
    public void setPq_deptname(String pq_deptname)
    {
        this.pq_deptname = pq_deptname;
    }
    
    public String getPq_categoryCodeList()
    {
        return pq_categoryCodeList;
    }
    
    public void setPq_categoryCodeList(String pq_categoryCodeList)
    {
        this.pq_categoryCodeList = pq_categoryCodeList;
    }
    
    public String getPq_inhospno()
    {
        return pq_inhospno;
    }
    
    public void setPq_inhospno(String pq_inhospno)
    {
        this.pq_inhospno = pq_inhospno;
    }
    
    public String getPq_patname()
    {
        return pq_patname;
    }
    
    public void setPq_patname(String pq_patname)
    {
        this.pq_patname = pq_patname;
    }
    
    public Integer getPq_sex()
    {
        return pq_sex;
    }
    
    public void setPq_sex(Integer pq_sex)
    {
        this.pq_sex = pq_sex;
    }
    
    public String getPq_caseId()
    {
        return pq_caseId;
    }
    
    public void setPq_caseId(String pq_caseId)
    {
        this.pq_caseId = pq_caseId;
    }
    
    public Date getPq_birthday()
    {
        return pq_birthday;
    }
    
    public void setPq_birthday(Date pq_birthday)
    {
        this.pq_birthday = pq_birthday;
    }
    
    public String getPq_age()
    {
        return pq_age;
    }
    
    public void setPq_age(String pq_age)
    {
        this.pq_age = pq_age;
    }
    
    public Integer getPq_ageunit()
    {
        return pq_ageunit;
    }
    
    public void setPq_ageunit(Integer pq_ageunit)
    {
        this.pq_ageunit = pq_ageunit;
    }
    
    public String getPq_avdp()
    {
        return pq_avdp;
    }
    
    public void setPq_avdp(String pq_avdp)
    {
        this.pq_avdp = pq_avdp;
    }
    
    public String getPq_sfysmc()
    {
        return pq_sfysmc;
    }
    
    public void setPq_sfysmc(String pq_sfysmc)
    {
        this.pq_sfysmc = pq_sfysmc;
    }
    
    public String getPq_bedno()
    {
        return pq_bedno;
    }
    
    public void setPq_bedno(String pq_bedno)
    {
        this.pq_bedno = pq_bedno;
    }
    
    public String getPq_zxrq()
    {
        return pq_zxrq;
    }
    
    public void setPq_zxrq(String pq_zxrq)
    {
        this.pq_zxrq = pq_zxrq;
    }
    
    public String getPq_zxsj()
    {
        return pq_zxsj;
    }
    
    public void setPq_zxsj(String pq_zxsj)
    {
        this.pq_zxsj = pq_zxsj;
    }
    
    public String getPq_pidsj()
    {
        return pq_pidsj;
    }
    
    public void setPq_pidsj(String pq_pidsj)
    {
        this.pq_pidsj = pq_pidsj;
    }
    
    public Integer getPq_isrepeat()
    {
        return pq_isrepeat;
    }
    
    public void setPq_isrepeat(Integer pq_isrepeat)
    {
        this.pq_isrepeat = pq_isrepeat;
    }
    
    public String getPq_deptcode()
    {
        return pq_deptcode;
    }
    
    public void setPq_deptcode(String pq_deptcode)
    {
        this.pq_deptcode = pq_deptcode;
    }
    
    public String getPq_pidrqzxbc()
    {
        return pq_pidrqzxbc;
    }
    
    public void setPq_pidrqzxbc(String pq_pidrqzxbc)
    {
        this.pq_pidrqzxbc = pq_pidrqzxbc;
    }
    
    public Integer getPq_yzlx()
    {
        return pq_yzlx;
    }
    
    public void setPq_yzlx(Integer pq_yzlx)
    {
        this.pq_yzlx = pq_yzlx;
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
    
    public Integer getPb_is0p()
    {
        return pb_is0p;
    }
    
    public void setPb_is0p(Integer pb_is0p)
    {
        this.pb_is0p = pb_is0p;
    }
    
    public Integer getScrqDelToday()
    {
        return scrqDelToday;
    }
    
    public void setScrqDelToday(Integer scrqDelToday)
    {
        this.scrqDelToday = scrqDelToday;
    }
    
    public String getPq_ydpqLast()
    {
        return pq_ydpqLast;
    }
    
    public void setPq_ydpqLast(String pq_ydpqLast)
    {
        this.pq_ydpqLast = pq_ydpqLast;
    }
    
    public Integer getPq_zxbcLast()
    {
        return pq_zxbcLast;
    }
    
    public void setPq_zxbcLast(Integer pq_zxbcLast)
    {
        this.pq_zxbcLast = pq_zxbcLast;
    }
    
    public Date getPq_printTime()
    {
        return pq_printTime;
    }
    
    public void setPq_printTime(Date pq_printTime)
    {
        this.pq_printTime = pq_printTime;
    }
    
    public String getPq_printTimeS()
    {
        return pq_printTimeS;
    }
    
    public void setPq_printTimeS(String pq_printTimeS)
    {
        this.pq_printTimeS = pq_printTimeS;
    }
    
}
