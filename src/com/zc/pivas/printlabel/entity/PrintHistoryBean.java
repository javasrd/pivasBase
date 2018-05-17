package com.zc.pivas.printlabel.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 瓶签历史实体
 *
 * @author kunkka
 * @version 1.0
 */
public class PrintHistoryBean implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -857232144759985614L;

    private Integer gid;

    private String pidsj;

    private String printInfo;

    private Integer pageIndex;

    private Date printDate;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getPrintInfo() {
        return printInfo;
    }

    public void setPrintInfo(String printInfo) {
        this.printInfo = printInfo;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPidsj() {
        return pidsj;
    }

    public void setPidsj(String pidsj) {
        this.pidsj = pidsj;
    }

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }

}
