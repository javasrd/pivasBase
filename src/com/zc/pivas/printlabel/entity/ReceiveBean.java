package com.zc.pivas.printlabel.entity;

import com.zc.pivas.docteradvice.entity.BatchBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author kunkka
 * @version 1.0
 */
public class ReceiveBean implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 4123983669704715096L;

    private String wardCode;

    private String wardName;

    private ArrayList<PrintPqBean> pqList;

    private ArrayList<BatchBean> pcList;

    /**
     * @return 返回 wardCode
     */
    public String getWardCode() {
        return wardCode;
    }

    /**
     * @param 对wardCode进行赋值
     */
    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    /**
     * @return 返回 wardName
     */
    public String getWardName() {
        return wardName;
    }

    /**
     * @param 对wardName进行赋值
     */
    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    /**
     * @return 返回 pcList
     */
    public ArrayList<BatchBean> getPcList() {
        return pcList;
    }

    /**
     * @param 对pcList进行赋值
     */
    public void setPcList(ArrayList<BatchBean> pcList) {
        this.pcList = pcList;
    }

    /**
     * @return 返回 pqList
     */
    public ArrayList<PrintPqBean> getPqList() {
        return pqList;
    }

    /**
     * @param 对pqList进行赋值
     */
    public void setPqList(ArrayList<PrintPqBean> pqList) {
        this.pqList = pqList;
    }


}
