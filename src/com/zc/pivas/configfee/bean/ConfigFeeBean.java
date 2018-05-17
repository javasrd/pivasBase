package com.zc.pivas.configfee.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 配置费/材料费bean
 *
 * @author kunkka
 * @version 1.0
 */
public class ConfigFeeBean implements Serializable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    private String gid;

    /**
     * 配置费/材料费编码
     */
    private String costCode;

    private String[] costCodes;

    /**
     * 名称
     */
    private String costName;

    private String[] costNames;

    /**
     * 价格
     */
    private String price;

    /**
     * 配置费的类别:0：普通药物、1：抗生素:2：细胞毒药物:3：TPN
     */
    private int configFeeType;

    /**
     * 配置费的类别:0：普通药物、1：抗生素:2：细胞毒药物:3：TPN
     */
    private String configFeeTypeName;

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

    public String[] getCostCodes() {
        return costCodes;
    }

    public void setCostCodes(String[] costCodes) {
        this.costCodes = costCodes;
    }

    public String[] getCostNames() {
        return costNames;
    }

    public void setCostNames(String[] costNames) {
        this.costNames = costNames;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getConfigFeeType() {
        return configFeeType;
    }

    public void setConfigFeeType(int configFeeType) {
        this.configFeeType = configFeeType;
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

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getConfigFeeTypeName() {
        return configFeeTypeName;
    }

    public void setConfigFeeTypeName(String configFeeTypeName) {
        this.configFeeTypeName = configFeeTypeName;
    }

    @Override
    public String toString() {
        return "ConfigFeeBean [gid=" + gid + ", costCode=" + costCode + ", costCodes=" + Arrays.toString(costCodes)
                + ", costName=" + costName + ", costNames=" + Arrays.toString(costNames) + ", price=" + price
                + ", configFeeType=" + configFeeType + ", configFeeTypeName=" + configFeeTypeName + ", reserve0="
                + reserve0 + ", reserve1=" + reserve1 + ", reserve2=" + reserve2 + "]";
    }

}
