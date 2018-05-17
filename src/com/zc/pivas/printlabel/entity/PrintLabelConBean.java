package com.zc.pivas.printlabel.entity;

import java.io.Serializable;

/**
 * 瓶签实体
 *
 * @author kunkka
 * @version 1.0
 */
public class PrintLabelConBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 条件名
     */
    private String name;

    /**
     * 用药日期： 0今日 1明日 2昨日
     */
    private String yyrq;

    /**
     * 批次
     */
    private String batchid;

    /**
     * 批次名称
     */
    private String batchName;

    /**
     * 药品分类
     */
    private String medicCategory;

    /**
     * 药品分类
     */
    private String medicCategoryID;

    /**
     * 药品标签
     */
    private String mediclabel;

    /**
     * 药品标签
     */
    private String medicLabelID;

    /**
     * 打印状态 0：否 1：是
     */
    private Integer printState;

    /**
     * 打印状态 0：否 1：是
     */
    private String printStateName;

    /**
     * 药品名称
     */
    private String medical;

    /**
     * 药品编码
     */
    private String medicalID;

    /**
     * 病区名
     */
    private String deptName;

    /**
     * 病区编码
     */
    private String deptCode;

    private String isPack;

    private Integer orderNum;

    private String isSame;

    /**
     * 是否用于药品统计
     */
    private Integer useType;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getMedicCategoryID() {
        return medicCategoryID;
    }

    public void setMedicCategoryID(String medicCategoryID) {
        this.medicCategoryID = medicCategoryID;
    }

    public String getMedicLabelID() {
        return medicLabelID;
    }

    public void setMedicLabelID(String medicLabelID) {
        this.medicLabelID = medicLabelID;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYyrq() {
        return yyrq;
    }

    public void setYyrq(String yyrq) {
        this.yyrq = yyrq;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getMedicCategory() {
        return medicCategory;
    }

    public void setMedicCategory(String medicCategory) {
        this.medicCategory = medicCategory;
    }

    public String getMediclabel() {
        return mediclabel;
    }

    public void setMediclabel(String mediclabel) {
        this.mediclabel = mediclabel;
    }

    public Integer getPrintState() {
        return printState;
    }

    public void setPrintState(Integer printState) {
        this.printState = printState;
    }

    public String getPrintStateName() {
        return printStateName;
    }

    public void setPrintStateName(String printStateName) {
        this.printStateName = printStateName;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getMedicalID() {
        return medicalID;
    }

    public void setMedicalID(String medicalID) {
        this.medicalID = medicalID;
    }

    /**
     * @return 返回 isPack
     */
    public String getIsPack() {
        return isPack;
    }

    /**
     */
    public void setIsPack(String isPack) {
        this.isPack = isPack;
    }

    /**
     * @return 返回 orderNum
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return 返回 isSame
     */
    public String getIsSame() {
        return isSame;
    }

    /**
     */
    public void setIsSame(String isSame) {
        this.isSame = isSame;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }


}
