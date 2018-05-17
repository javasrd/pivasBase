package com.zc.pivas.printlabel.entity;

import java.io.Serializable;

/**
 * 打印机配置bean
 *
 * @author kunkka
 * @version 1.0
 */

public class PrinterIPBean implements Serializable {

    private static final long serialVersionUID = -1814612685366075372L;

    /**
     * id
     */
    private Long gid;

    /**
     * 主机名称
     */
    private String compName;

    /**
     * IP地址
     */
    private String ipAddr;

    /**
     * 打印机名称
     */
    private String prinName;

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getPrinName() {
        return prinName;
    }

    public void setPrinName(String prinName) {
        this.prinName = prinName;
    }

}
