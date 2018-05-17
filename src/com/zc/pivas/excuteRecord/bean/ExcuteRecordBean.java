package com.zc.pivas.excuteRecord.bean;


import com.zc.base.common.dao.AbstractDO;

import java.util.Date;


/**
 * 药单执行记录
 *
 * @author cacabin
 * @version 1.0
 */
public class ExcuteRecordBean extends AbstractDO {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 医嘱编码
     */
    private String recipeID;

    /**
     * 组号
     */
    private String groupNo;

    /**
     * 药单号
     */
    private String drugListID;

    /**
     * 用药频次
     */
    private String drugFreq;

    /**
     * 药品编码
     */
    private String drugCode;

    /**
     * 药品名称
     */
    private String drugName;

    /**
     * 药品数量
     */
    private String quantity;

    /**
     * 数量单位
     */
    private String quantityUnit;

    /**
     * 执行序号
     */
    private String schedule;

    /**
     * 用药时间
     */
    private String occDT;

    /**
     * 收费时间
     */
    private String chargeDT;

    /**
     * 输液时间
     */
    private String infusionDate;

    /**
     * 瓶签号/或药单唯一号
     */
    private String labelNo;

    /**
     * 药单起始时间
     */
    private String begindt;

    /**
     * 药单终止时间
     */
    private String enddt;

    private Date synDate;

    /**
     * 药单执行批次
     */
    private String batchID = "";

    /**
     * 药单全天用药次数
     */
    private String useCount = "";

    /**
     * 药单执行记录状态 0：正常  1：停止 2:退费
     */
    private String state;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 病人姓名
     */
    private String patName;

    /**
     * 病区编码
     */
    private String wardCode;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 药单退费处理状态 0：未处理  1：已处理
     */
    private String processState;

    /**
     * 对应审方错误类型
     */
    private String errorType;

    /**
     * 对应审方错误类型
     */
    private String errorTypeName;

    public String getErrorTypeName() {
        return errorTypeName == null ? "" : errorTypeName;
    }

    public void setErrorTypeName(String errorTypeName) {
        this.errorTypeName = errorTypeName;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getUseCount() {
        return useCount;
    }

    public void setUseCount(String useCount) {
        this.useCount = useCount;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getDrugListID() {
        return drugListID;
    }

    public void setDrugListID(String drugListID) {
        this.drugListID = drugListID;
    }

    public String getDrugFreq() {
        return drugFreq;
    }

    public void setDrugFreq(String drugFreq) {
        this.drugFreq = drugFreq;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getOccDT() {
        return occDT;
    }

    public void setOccDT(String occDT) {
        this.occDT = occDT;
    }

    public String getChargeDT() {
        return chargeDT;
    }

    public void setChargeDT(String chargeDT) {
        this.chargeDT = chargeDT;
    }

    public String getInfusionDate() {
        return infusionDate;
    }

    public void setInfusionDate(String infusionDate) {
        this.infusionDate = infusionDate;
    }

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }

    public String getBegindt() {
        return begindt;
    }

    public void setBegindt(String begindt) {
        this.begindt = begindt;
    }

    public String getEnddt() {
        return enddt;
    }

    public void setEnddt(String enddt) {
        this.enddt = enddt;
    }

    /**
     * @return 返回 state
     */
    public String getState() {
        return state;
    }

    /**
     * @param 对state进行赋值
     */
    public void setState(String state) {
        this.state = state;
    }

}
