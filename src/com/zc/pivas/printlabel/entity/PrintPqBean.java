package com.zc.pivas.printlabel.entity;

import java.io.Serializable;

/**
 * @author kunkka
 * @version 1.0
 */
public class PrintPqBean implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 3648110723086433541L;

    private String bedNo;

    private String patname;

    private int total;

    private String[] pqArray;

    private String ydpq;

    /**
     * @return 返回 bedNo
     */
    public String getBedNo() {
        return bedNo;
    }

    /**
     * @param 对bedNo进行赋值
     */
    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    /**
     * @return 返回 patname
     */
    public String getPatname() {
        return patname;
    }

    /**
     * @param 对patname进行赋值
     */
    public void setPatname(String patname) {
        this.patname = patname;
    }


    /**
     * @return 返回 pqArray
     */
    public String[] getPqArray() {
        return pqArray;
    }

    /**
     * @param 对pqArray进行赋值
     */
    public void setPqArray(String[] pqArray) {
        this.pqArray = pqArray;
    }

    /**
     * @return 返回 ydpq
     */
    public String getYdpq() {
        return ydpq;
    }

    /**
     * @param 对ydpq进行赋值
     */
    public void setYdpq(String ydpq) {
        this.ydpq = ydpq;
    }

    /**
     * @return 返回 total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param 对total进行赋值
     */
    public void setTotal(int total) {
        this.total = total;
    }


}
