package com.zc.pivas.configfee.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 配置费规则bean
 *
 * @author kunkka
 * @version 1.0
 */
public class ConfigFeeRuleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String gid;

    /**
     * 规则名称
     */
    private String ruleName;

    private String[] ruleNames;

    /**
     * 药品分类编码
     */
    private String drugTypeCode;

    /**
     * 药品分类名称
     */
    private String drugTypeName;

    private String[] drugTypeNames;

    /**
     * 药品（可多选，用；分开）
     */
    private String drugCode;

    /**
     * 药品名称（可多选，用；分开）
     */
    private String drugName;

    /**
     * 容积条件
     */
    private String volume;

    private String[] volumes;

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


    private String isSameName;

    /**
     * 配置费规则明细列表
     */
    private List<ConfigFeeDetailBean> configFeeDetailList;

    public List<ConfigFeeDetailBean> getConfigFeeDetailList() {
        return configFeeDetailList;
    }

    public void setConfigFeeDetailList(List<ConfigFeeDetailBean> configFeeDetailList) {
        this.configFeeDetailList = configFeeDetailList;
    }

    public String getDrugTypeName() {
        return drugTypeName;
    }

    public void setDrugTypeName(String drugTypeName) {
        this.drugTypeName = drugTypeName;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String[] getRuleNames() {
        return ruleNames;
    }

    public void setRuleNames(String[] ruleNames) {
        this.ruleNames = ruleNames;
    }

    public String[] getDrugTypeNames() {
        return drugTypeNames;
    }

    public void setDrugTypeNames(String[] drugTypeNames) {
        this.drugTypeNames = drugTypeNames;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getDrugTypeCode() {
        return drugTypeCode;
    }

    public void setDrugTypeCode(String drugTypeCode) {
        this.drugTypeCode = drugTypeCode;
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

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
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

    public String[] getVolumes() {
        return volumes;
    }

    public void setVolumes(String[] volumes) {
        this.volumes = volumes;
    }

    @Override
    public String toString() {
        return "ConfigFeeRuleBean [gid=" + gid + ", ruleName=" + ruleName + ", ruleNames=" + Arrays.toString(ruleNames)
                + ", drugTypeCode=" + drugTypeCode + ", drugTypeName=" + drugTypeName + ", drugTypeNames="
                + Arrays.toString(drugTypeNames) + ", drugCode=" + drugCode + ", drugName=" + drugName + ", volume="
                + volume + ", volumes=" + Arrays.toString(volumes) + ", reserve0=" + reserve0 + ", reserve1=" + reserve1
                + ", reserve2=" + reserve2 + ", configFeeDetailList=" + configFeeDetailList + "]";
    }

    /**
     * @return 返回 isSameName
     */
    public String getIsSameName() {
        return isSameName;
    }

    /**
     * @param 对isSameName进行赋值
     */
    public void setIsSameName(String isSameName) {
        this.isSameName = isSameName;
    }

}
