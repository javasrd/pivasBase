package com.zc.pivas.synresult.bean;

import java.io.Serializable;

/**
 * 收取配置费用
 *
 * @author kunkka
 * @version 1.0
 */
public class ConfigFeeTaskBean implements Serializable {

    private static final long serialVersionUID = 1L;

    //对应pivas_yd_main中pidsj，唯一
    private String yd_pidsj;

    //配置费/材料费 编码
    private String costCode;

    //住院流水号
    private String caseID;

    //人工号
    private String amount;

    //费用
    private String account;

    //收费结果   1:成功  -1:失败 0:调用配置费收费失败'
    private Integer result = null;

    private String resultDesc = "";

    //增加时间
    private String addDate = "";

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getYd_pidsj() {
        return yd_pidsj;
    }

    public void setYd_pidsj(String yd_pidsj) {
        this.yd_pidsj = yd_pidsj;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
