package com.zc.pivas.patient.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 病人实体类
 *
 * @author Ray
 * @version 1.0
 */
public class PatientBean implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String gid;

    /**
     * 住院流水号，病人唯一标识
     */
    private String inhospNo;

    private String[] inhospNos;

    /**
     * 患者姓名
     */
    private String patName;

    private String[] patNames;

    /**
     * 性别：0女，1男
     */
    private Integer sex;

    /**
     * 性别
     */
    private String sexName;

    /**
     * 病人当前病区(科室）
     */
    private String wardCode;

    /**
     * 病人当前病区名称
     */
    private String wardName;

    /**
     * 病人当前状态 -1:预出院
     */
    private String state;

    /**
     * 患者住院期间，所住床位对应的编号
     */
    private String bedNo;

    /**
     * 病人唯一住院号
     */
    private String case_ID;

    /**
     * 病人出生日期
     */
    private String birthDay;

    private Timestamp birth;

    /**
     * 年龄
     */
    private String age;

    /**
     * 年龄单位，0天 1月 2年
     */
    private int ageUnit;

    /**
     * 年龄和单位的组合
     */
    private String ageDetail;

    /**
     * 病人体重
     */
    private String avdp;

    /**
     * 预留字段0
     */
    private String reserve0;

    /**
     * 预留字段1
     */
    private String reserve1;

    /**
     * 预留字段2
     */
    private String reserve2;

    private String[] drugnames;

    private String[] doctors;

    /**
     * 预出院状态
     */
    //private String hosOutSta = "";

    /**
     * 预出院时间
     */
    private String hosOutTime = "";

    public String getHosOutTime() {
        return hosOutTime;
    }

    public void setHosOutTime(String hosOutTime) {
        this.hosOutTime = hosOutTime;
    }

    public String[] getInhospNos() {
        return inhospNos;
    }

    public void setInhospNos(String[] inhospNos) {
        this.inhospNos = inhospNos;
    }

    public String[] getPatNames() {
        return patNames;
    }

    public void setPatNames(String[] patNames) {
        this.patNames = patNames;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getInhospNo() {
        return inhospNo;
    }

    public void setInhospNo(String inhospNo) {
        this.inhospNo = inhospNo;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getCase_ID() {
        return case_ID;
    }

    public void setCase_ID(String caseID) {
        case_ID = caseID;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(int ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getAgeDetail() {
        return ageDetail;
    }

    public void setAgeDetail(String ageDetail) {
        this.ageDetail = ageDetail;
    }

    public String getAvdp() {
        return avdp;
    }

    public void setAvdp(String avdp) {
        this.avdp = avdp;
    }

    public String getReserve0() {
        return reserve0;
    }

    public void setReserve0(String reserve0) {
        this.reserve0 = reserve0;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public Timestamp getBirth() {
        return birth;
    }

    public void setBirth(Timestamp birth) {
        this.birth = birth;
    }

    public String[] getDrugnames() {
        return drugnames;
    }

    public void setDrugnames(String[] drugnames) {
        this.drugnames = drugnames;
    }

    public String[] getDoctors() {
        return doctors;
    }

    public void setDoctors(String[] doctors) {
        this.doctors = doctors;
    }

    @Override
    public String toString() {
        return "PatientBean [gid=" + gid + ", inhospNo=" + inhospNo + ", inhospNos=" + Arrays.toString(inhospNos) + ", patName=" + patName + ", patNames=" + Arrays.toString(patNames) + ", sex=" + sex + ", sexName=" + sexName + ", wardCode=" + wardCode + ", wardName=" + wardName + ", state=" + state + ", bedNo=" + bedNo + ", case_ID=" + case_ID + ", birthDay=" + birthDay + ", birth=" + birth + ", age=" + age + ", ageUnit=" + ageUnit + ", ageDetail=" + ageDetail + ", avdp=" + avdp + ", reserve0=" + reserve0 + ", reserve1=" + reserve1 + ", reserve2=" + reserve2 + "]";
    }

}
