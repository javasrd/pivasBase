package com.zc.pivas.configfee.bean;

/**
 * 配置费情况明细类
 *
 * @author kunkka
 * @version 1.0
 */
public class ConfigFeeChargeBean {

    // 病人住院流水号
    String inpatientNo;

    // 收费编码
    String costcode;

    // 数量
    String amount;

    // 收费人工号
    String zxgh;

    // 配置费规则的主键ID
    String configfeeruleid;

    // 收费编码的价格
    String price;

    public String getInpatientNo() {
        return inpatientNo;
    }

    public void setInpatientNo(String inpatientNo) {
        this.inpatientNo = inpatientNo;
    }

    public String getCostCode() {
        return costcode;
    }

    public void setCostCode(String CostCode) {
        this.costcode = CostCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getZxgh() {
        return zxgh;
    }

    public void setZxgh(String zxgh) {
        this.zxgh = zxgh;
    }

    public String getconfigfeeruleid() {
        return configfeeruleid;
    }

    public void setconfigfeeruleid(String configfeeruleid) {
        this.configfeeruleid = configfeeruleid;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ConfigFeeChargeBean [inpatientNo=" + inpatientNo + ", costCode=" + costcode + ", amount=" + amount
                + ", zxgh=" + zxgh + ", configfeeruleid=" + configfeeruleid + ", price=" + price + "]";
    }
}
