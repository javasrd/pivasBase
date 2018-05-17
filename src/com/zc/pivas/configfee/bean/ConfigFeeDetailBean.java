package com.zc.pivas.configfee.bean;

import java.io.Serializable;

/**
 * 配置费规则bean
 *
 * @author kunkka
 * @version 1.0
 */
public class ConfigFeeDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置费明细编码
     */
    private String detailCode;

    /**
     * 配置费/材料费编码
     */
    private String costCode;

    /**
     * 配置费/材料费名称
     */
    private String costName;

    /**
     * 数量
     */
    private String amount = "1";

    /**
     * 是否每天收取一次(0：是，1：否)
     */
    private int rate;

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

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
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

    @Override
    public String toString() {
        return "ConfigFeeDetailBean [amount=" + amount + ", costCode=" + costCode + ", costName=" + costName
                + ", detailCode=" + detailCode + ", rate=" + rate + ", reserve0=" + reserve0 + ", reserve1=" + reserve1
                + ", reserve2=" + reserve2 + "]";
    }

}
