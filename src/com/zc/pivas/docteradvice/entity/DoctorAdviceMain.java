package com.zc.pivas.docteradvice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 医嘱主表
 *
 * @author  cacabin
 * @version  1.0
 */
public class DoctorAdviceMain implements Serializable
{
    
    private static final long serialVersionUID = 1L;

    /**
     * 病区拼音 
     */
    String wardNameEn;

    /**
     * 床号拼音
     */
    String bednoEn;

    /**
     * 执行频率
     */
    String supplyName;

    /**
     * 父医嘱id
     */
    Long yzMainId;

    /**
     * 医嘱编号
     */
    String actOrderNo;

    /**
     * 父医嘱编号
     */
    String parentNo;

    /**
     *  病区编码
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
     * 开嘱医生的工号
     */
    String doctor;

    /**
     * 开嘱医生姓名
     */
    String doctorName;

    /**
     *  录入医生工号
     */
    String drawer;

    /**
     * 录入医生姓名
     */
    String drawername;

    /**
     *  医嘱的执行频率
     */
    String freqCode;

    /**
     * 用药途径
     */
    String supplyCode;

    /**
     *  药品编码
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
     *  单次剂量
     */
    String dose;

    /**
     * 单次剂量单位
     */
    String doseUnit;

    /**
     * 药品数量
     */
    String quantity;

    /**
     * 医嘱开始时间
     */
    Date startTime;

    /**
     * 医嘱开始时间
     */
    String startTimeS;

    /**
     * 医嘱结束时间
     */
    Date endTime;

    /**
     * 医嘱结束时间
     */
    String endTimeS;

    /**
     * 备注
     */
    String remark;

    /**
     * 医嘱类型
     */
    Integer yzlx;

    /**
     * 医嘱类型
     */
    String yzlxS;

    /**
     * 医嘱状态
     */
    Integer yzzt;

    /**
     * 医嘱状态
     */
    String yzztS;

    /**
     * 医嘱审核状态
     */
    Integer yzshzt;

    /**
     * 医嘱审核状态
     */
    String yzshztS;

    /**
     * 审方问题
     */
    Integer yzshbtglx;

    /**
     * 审方问题
     */
    String yzshbtglxS;
    
    /**
     * 审方问题颜色
     */
    String yzshbtglxColor;

    /**
     * 医嘱自动审核状态
     */
    Integer yzzdshzt;

    /**
     * 医嘱自动审核状态
     */
    String yzzdshztS;

    /**
     * 自动审方问题
     */
    Integer yzzdshbtglx;

    /**
     * 自动审方问题
     */
    String yzzdshbtglxS;

    /**
     * 医嘱审核不通过原因备注
     */
    String yzshbtgyy;

    /**
     * 自备
     */
    Integer selfbuy;

    /**
     * 自备
     */
    String selfbuyS;

    /**
     * 是否营养液
     */
    Integer tpn;

    /**
     * 医嘱审核药师名名称
     */
    String sfysmc;

    /**
     *  医嘱审核药师名code
     */
    String sfyscode;

    /**
     * 审方日期
     */
    Date sfrq;

    /**
     * 审方日期
     */
    String sfrqS;

    /**
     * 
     */
    String reserve1;

    /**
     * 
     */
    String reserve2;

    /**
     * 
     */
    String reserve3;

    /**
     * 包装单位
     */
    String medicamentsPackingUnit;

    /**
     * 病人id
     */
    String p_gid;

    /**
     * 病人住院号
     */
    String p_inhospno;

    /**
     *  病人姓名
     */
    String p_patname;

    /**
     * 性别
     */
    Integer p_sex;

    /**
     * 性别
     */
    String p_sexS;

    /**
     * 病区code
     */
    String p_wardcode;

    /**
     * 病人状态
     */
    String p_state;

    /**
     *  床号
     */
    String p_bedno;

    /**
     * 
     */
    String p_reserve0;

    /**
     * 
     */
    String p_reserve1;

    /**
     * 
     */
    String p_reserve2;

    /**
     *  病人唯一住院号
     */
    String p_case_id;

    /**
     * 病人出生日期
     */
    Date p_birthday;

    /**
     * 病人出生日期
     */
    String p_birthdayS;

    /**
     * 病人年龄
     */
    String p_age;

    /**
     * 年龄单位
     */
    Integer p_ageunit;

    /**
     * 病人体重
     */
    String p_avdp;

    /**
     * 病区id
     */
    String d_gid;

    /**
     * 病区code
     */
    String d_deptcode;

    /**
     * 病区名称
     */
    String d_deptname;

    /**
     * 是否入仓
     */
    Integer rucangOKNum;

    /**
     * 是否入仓
     */
    String rucangOKNumS;

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
    
    String recheckcause;
    
    String recheckstate;
    
    /**
     * 是否选中
     */
    boolean checked = false;

    /**
     * 是否展开
     */
    boolean expanded = false;

    /**
     * 子节点个数
     */
    Integer childNum = 0;

    /**
     * 图标
     */
    String iconCls = "no-icon";

    /**
     * 分组名称
     */
    String groupName = "";

    /**
     * 子节点
     */
    List<DoctorAdviceMain> children = new ArrayList<DoctorAdviceMain>();

    /**
     * 病人姓名
     */
    String patname;

    /**
     * 病人性别
     */
    Integer sex;

    /**
     *  出生日期
     */
    Date birthday;

    /**
     *  出生日期
     */
    String birthdayS;

    /**
     * 年龄
     */
    String age;

    /**
     *  年龄单位
     */
    Integer ageunit;

    /**
     * 体重
     */
    String avdp;

    /**
     * 输液量
     */
    String transfusion;

    /**
     *  滴速
     */
    String dropSpeed;

    /**
     * 临时医嘱来源
     */
    Integer yzResource;

    /**
     * 药品名称
     */
    String medicamentsName;

    /**
     * 同步时间
     */
    String synDate;

    /**
     * 开立时间相比今日天数
     */
    Integer startDayDelNum;

    /**
     * 开立时间 小时
     */
    Integer startHour;

    /**
     * 入仓是否检查
     */
    Integer rucangNeedCheck;

    /**
     * 医嘱确认状态
     */
    Integer yzconfigStatus;

    /**
     * 医嘱确认时间
     */
    Date yzconfigTime;

    /**
     * 医嘱确认人id
     */
    Integer yzconfigUid;

    /**
     * 医嘱确认人
     */
    String yzconfigUname;

    /**
     * 药单优化code
     */
    Integer ydreorderCode;

    /**
     * 药单优化时间
     */
    Date ydreorderTime;

    /**
     * 药单优化信息
     */
    String ydreorderMess;
    
    /**
     * 医嘱首日用药次数
     */
    private String firstUseCount;
    
    /**
     * 审核时间
     */
    private String checkDate;
    
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
    
    public Integer getRucangNeedCheck()
    {
        return rucangNeedCheck;
    }
    
    public void setRucangNeedCheck(Integer rucangNeedCheck)
    {
        this.rucangNeedCheck = rucangNeedCheck;
    }
    
    public String getSynDate()
    {
        return synDate;
    }
    
    public void setSynDate(String synDate)
    {
        this.synDate = synDate;
    }
    
    public Long getYzMainId()
    {
        return yzMainId;
    }
    
    public void setYzMainId(Long yzMainId)
    {
        this.yzMainId = yzMainId;
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
    
    public String getDrawer()
    {
        return drawer;
    }
    
    public void setDrawer(String drawer)
    {
        this.drawer = drawer;
    }
    
    public String getDrawername()
    {
        return drawername;
    }
    
    public void setDrawername(String drawername)
    {
        this.drawername = drawername;
    }
    
    public String getFreqCode()
    {
        return freqCode;
    }
    
    public void setFreqCode(String freqCode)
    {
        this.freqCode = freqCode;
    }
    
    public String getSupplyCode()
    {
        return supplyCode;
    }
    
    public void setSupplyCode(String supplyCode)
    {
        this.supplyCode = supplyCode;
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
    
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getYzlx()
    {
        return yzlx;
    }
    
    public void setYzlx(Integer yzlx)
    {
        this.yzlx = yzlx;
    }
    
    public Integer getYzzt()
    {
        return yzzt;
    }
    
    public void setYzzt(Integer yzzt)
    {
        this.yzzt = yzzt;
    }
    
    public Integer getYzshzt()
    {
        return yzshzt;
    }
    
    public void setYzshzt(Integer yzshzt)
    {
        this.yzshzt = yzshzt;
    }
    
    public Integer getYzshbtglx()
    {
        return yzshbtglx;
    }
    
    public void setYzshbtglx(Integer yzshbtglx)
    {
        this.yzshbtglx = yzshbtglx;
    }
    
    public Integer getYzzdshzt()
    {
        return yzzdshzt;
    }
    
    public void setYzzdshzt(Integer yzzdshzt)
    {
        this.yzzdshzt = yzzdshzt;
    }
    
    public Integer getYzzdshbtglx()
    {
        return yzzdshbtglx;
    }
    
    public void setYzzdshbtglx(Integer yzzdshbtglx)
    {
        this.yzzdshbtglx = yzzdshbtglx;
    }
    
    public String getYzshbtgyy()
    {
        return yzshbtgyy;
    }
    
    public void setYzshbtgyy(String yzshbtgyy)
    {
        this.yzshbtgyy = yzshbtgyy;
    }
    
    public Integer getSelfbuy()
    {
        return selfbuy;
    }
    
    public void setSelfbuy(Integer selfbuy)
    {
        this.selfbuy = selfbuy;
    }
    
    public Integer getTpn()
    {
        return tpn;
    }
    
    public void setTpn(Integer tpn)
    {
        this.tpn = tpn;
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
    
    public String getP_gid()
    {
        return p_gid;
    }
    
    public void setP_gid(String p_gid)
    {
        this.p_gid = p_gid;
    }
    
    public String getP_inhospno()
    {
        return p_inhospno;
    }
    
    public void setP_inhospno(String p_inhospno)
    {
        this.p_inhospno = p_inhospno;
    }
    
    public String getP_patname()
    {
        return p_patname;
    }
    
    public void setP_patname(String p_patname)
    {
        this.p_patname = p_patname;
    }
    
    public Integer getP_sex()
    {
        return p_sex;
    }
    
    public void setP_sex(Integer p_sex)
    {
        this.p_sex = p_sex;
    }
    
    public String getP_wardcode()
    {
        return p_wardcode;
    }
    
    public void setP_wardcode(String p_wardcode)
    {
        this.p_wardcode = p_wardcode;
    }
    
    public String getP_state()
    {
        return p_state;
    }
    
    public void setP_state(String p_state)
    {
        this.p_state = p_state;
    }
    
    public String getP_bedno()
    {
        return p_bedno;
    }
    
    public void setP_bedno(String p_bedno)
    {
        this.p_bedno = p_bedno;
    }
    
    public String getP_reserve0()
    {
        return p_reserve0;
    }
    
    public void setP_reserve0(String p_reserve0)
    {
        this.p_reserve0 = p_reserve0;
    }
    
    public String getP_reserve1()
    {
        return p_reserve1;
    }
    
    public void setP_reserve1(String p_reserve1)
    {
        this.p_reserve1 = p_reserve1;
    }
    
    public String getP_reserve2()
    {
        return p_reserve2;
    }
    
    public void setP_reserve2(String p_reserve2)
    {
        this.p_reserve2 = p_reserve2;
    }
    
    public String getP_case_id()
    {
        return p_case_id;
    }
    
    public void setP_case_id(String p_case_id)
    {
        this.p_case_id = p_case_id;
    }
    
    public Date getP_birthday()
    {
        return p_birthday;
    }
    
    public void setP_birthday(Date p_birthday)
    {
        this.p_birthday = p_birthday;
    }
    
    public String getP_age()
    {
        return p_age;
    }
    
    public void setP_age(String p_age)
    {
        this.p_age = p_age;
    }
    
    public Integer getP_ageunit()
    {
        return p_ageunit;
    }
    
    public void setP_ageunit(Integer p_ageunit)
    {
        this.p_ageunit = p_ageunit;
    }
    
    public String getP_avdp()
    {
        return p_avdp;
    }
    
    public void setP_avdp(String p_avdp)
    {
        this.p_avdp = p_avdp;
    }
    
    public String getD_gid()
    {
        return d_gid;
    }
    
    public void setD_gid(String d_gid)
    {
        this.d_gid = d_gid;
    }
    
    public String getD_deptcode()
    {
        return d_deptcode;
    }
    
    public void setD_deptcode(String d_deptcode)
    {
        this.d_deptcode = d_deptcode;
    }
    
    public String getD_deptname()
    {
        return d_deptname;
    }
    
    public void setD_deptname(String d_deptname)
    {
        this.d_deptname = d_deptname;
    }
    
    public Integer getRucangOKNum()
    {
        return rucangOKNum;
    }
    
    public void setRucangOKNum(Integer rucangOKNum)
    {
        this.rucangOKNum = rucangOKNum;
    }
    
    public String getStartTimeS()
    {
        return startTimeS;
    }
    
    public void setStartTimeS(String startTimeS)
    {
        this.startTimeS = startTimeS;
    }
    
    public String getEndTimeS()
    {
        return endTimeS;
    }
    
    public void setEndTimeS(String endTimeS)
    {
        this.endTimeS = endTimeS;
    }
    
    public String getSfrqS()
    {
        return sfrqS;
    }
    
    public void setSfrqS(String sfrqS)
    {
        this.sfrqS = sfrqS;
    }
    
    public String getP_birthdayS()
    {
        return p_birthdayS;
    }
    
    public void setP_birthdayS(String p_birthdayS)
    {
        this.p_birthdayS = p_birthdayS;
    }
    
    public String getMedicamentsPackingUnit()
    {
        return medicamentsPackingUnit;
    }
    
    public void setMedicamentsPackingUnit(String medicamentsPackingUnit)
    {
        this.medicamentsPackingUnit = medicamentsPackingUnit;
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
    
    public boolean isChecked()
    {
        return checked;
    }
    
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    
    public boolean isExpanded()
    {
        return expanded;
    }
    
    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }
    
    public Integer getChildNum()
    {
        return childNum;
    }
    
    public void setChildNum(Integer childNum)
    {
        this.childNum = childNum;
    }
    
    public String getIconCls()
    {
        return iconCls;
    }
    
    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }
    
    public List<DoctorAdviceMain> getChildren()
    {
        return children;
    }
    
    public void setChildren(List<DoctorAdviceMain> children)
    {
        this.children = children;
    }
    
    public String getGroupName()
    {
        return groupName;
    }
    
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    
    public String getYzlxS()
    {
        return yzlxS;
    }
    
    public void setYzlxS(String yzlxS)
    {
        this.yzlxS = yzlxS;
    }
    
    public String getYzztS()
    {
        return yzztS;
    }
    
    public void setYzztS(String yzztS)
    {
        this.yzztS = yzztS;
    }
    
    public String getYzshztS()
    {
        return yzshztS;
    }
    
    public void setYzshztS(String yzshztS)
    {
        this.yzshztS = yzshztS;
    }
    
    public String getYzshbtglxS()
    {
        return yzshbtglxS;
    }
    
    public void setYzshbtglxS(String yzshbtglxS)
    {
        this.yzshbtglxS = yzshbtglxS;
    }
    
    public String getYzzdshztS()
    {
        return yzzdshztS;
    }
    
    public void setYzzdshztS(String yzzdshztS)
    {
        this.yzzdshztS = yzzdshztS;
    }
    
    public String getYzzdshbtglxS()
    {
        return yzzdshbtglxS;
    }
    
    public void setYzzdshbtglxS(String yzzdshbtglxS)
    {
        this.yzzdshbtglxS = yzzdshbtglxS;
    }
    
    public String getSelfbuyS()
    {
        return selfbuyS;
    }
    
    public void setSelfbuyS(String selfbuyS)
    {
        this.selfbuyS = selfbuyS;
    }
    
    public String getP_sexS()
    {
        return p_sexS;
    }
    
    public void setP_sexS(String p_sexS)
    {
        this.p_sexS = p_sexS;
    }
    
    public String getRucangOKNumS()
    {
        return rucangOKNumS;
    }
    
    public void setRucangOKNumS(String rucangOKNumS)
    {
        this.rucangOKNumS = rucangOKNumS;
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
    
    public String getAvdp()
    {
        return avdp;
    }
    
    public void setAvdp(String avdp)
    {
        this.avdp = avdp;
    }
    
    public String getMedicamentsName()
    {
        return medicamentsName;
    }
    
    public void setMedicamentsName(String medicamentsName)
    {
        this.medicamentsName = medicamentsName;
    }
    
    public String getBirthdayS()
    {
        return birthdayS;
    }
    
    public void setBirthdayS(String birthdayS)
    {
        this.birthdayS = birthdayS;
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
    
    public String getSupplyName()
    {
        return supplyName;
    }
    
    public void setSupplyName(String supplyName)
    {
        this.supplyName = supplyName;
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

    /**
     * @return 返回 yzshbtglxColor
     */
    public String getYzshbtglxColor()
    {
        return yzshbtglxColor;
    }

    /**
     * @param
     */
    public void setYzshbtglxColor(String yzshbtglxColor)
    {
        this.yzshbtglxColor = yzshbtglxColor;
    }

    /**
     * @return 返回 recheckcause
     */
    public String getRecheckcause()
    {
        return recheckcause;
    }

    /**
     * @param
     */
    public void setRecheckcause(String recheckcause)
    {
        this.recheckcause = recheckcause;
    }

    /**
     * @return 返回 recheckstate
     */
    public String getRecheckstate()
    {
        return recheckstate;
    }

    /**
     * @param
     */
    public void setRecheckstate(String recheckstate)
    {
        this.recheckstate = recheckstate;
    }
    
}
