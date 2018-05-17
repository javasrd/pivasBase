package com.zc.pivas.synresult.bean;

import java.io.Serializable;

/**
 * 医嘱状态检查
 *
 * @author kunkka
 * @version 1.0
 */
public class CheckOrderStatusRespon implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderGroupNo;

    //0：执行 1：停止 2：撤销 3:医嘱药品变动 4医嘱频次变动5医嘱滴速变动
    private String result;

    //校验内同描述
    private String resultDesc = "医嘱正常";

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getOrderGroupNo() {
        return orderGroupNo;
    }

    public void setOrderGroupNo(String orderGroupNo) {
        this.orderGroupNo = orderGroupNo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CheckOrderStatusRespon [orderGroupNo=" + orderGroupNo + ", result=" + result + "]";
    }

}
