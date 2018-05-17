package com.zc.pivas.printlabel.entity;

import java.io.Serializable;

/**
 * 瓶签日志DAO
 *
 * @author kunkka
 * @version 1.0
 */

public class PrintLogBean implements Serializable {


    /**
     * 注释内容
     */
    private static final long serialVersionUID = 5864781110029745252L;

    /**
     * 主键
     */
    private Long gid;

    /**
     * 操作人
     */
    private String opreName;

    private String[] opreNames;

    /**
     * 打印序号
     */
    private Integer printIndex;

    private String[] printIndexs;

    /**
     * 打印条件
     */
    private String printCondition;

    /**
     * 打印日期
     */
    private String printDate;


    /**
     * 打印开始日期
     */
    private String printStart;

    /**
     * 打印结束日期
     */
    private String printEnd;

    public String getPrintStart() {
        return printStart;
    }

    public void setPrintStart(String printStart) {
        this.printStart = printStart;
    }

    public String getPrintEnd() {
        return printEnd;
    }

    public void setPrintEnd(String printEnd) {
        this.printEnd = printEnd;
    }

    public String[] getOpreNames() {
        return opreNames;
    }

    public void setOpreNames(String[] opreNames) {
        this.opreNames = opreNames;
    }

    public String[] getPrintIndexs() {
        return printIndexs;
    }

    public void setPrintIndexs(String[] printIndexs) {
        this.printIndexs = printIndexs;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getOpreName() {
        return opreName;
    }

    public void setOpreName(String opreName) {
        this.opreName = opreName;
    }

    public Integer getPrintIndex() {
        return printIndex;
    }

    public void setPrintIndex(Integer printIndex) {
        this.printIndex = printIndex;
    }

    public String getPrintCondition() {
        return printCondition;
    }

    public void setPrintCondition(String printCondition) {
        this.printCondition = printCondition;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

}
