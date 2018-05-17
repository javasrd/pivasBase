package com.zc.pivas.synresult.bean;

import java.io.Serializable;

/**
 * 收取配置费接口
 *
 * @author kunkka
 * @version 1.0
 */
public class SetFymxDataReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 住院流水号
     */
    private String alzyh;

    //医院内部序号
    private String alfyxh;

    /**
     * 工号
     */
    private String aszxgh;

    /**
     * 费用数量
     */
    private String adfysl;

    public String getAlzyh() {
        return alzyh;
    }

    public String getAdfysl() {
        return adfysl;
    }

    public void setAdfysl(String adfysl) {
        this.adfysl = adfysl;
    }

    public void setAlzyh(String alzyh) {
        this.alzyh = alzyh;
    }

    public String getAlfyxh() {
        return alfyxh;
    }

    public void setAlfyxh(String alfyxh) {
        this.alfyxh = alfyxh;
    }

    public String getAszxgh() {
        return aszxgh;
    }

    public void setAszxgh(String aszxgh) {
        this.aszxgh = aszxgh;
    }

    @Override
    public String toString() {
        return "SetFymxDataReq [adfysl=" + adfysl + ", alfyxh=" + alfyxh + ", alzyh=" + alzyh + ", aszxgh=" + aszxgh
                + "]";
    }

}
