package com.zc.pivas.titileshow.bean;

import java.io.Serializable;

/**
 * 医嘱列表配置Bean
 * @author kunkka
 * @version 1.0
 */

public class ConfigTitleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long confId;

    private String confName;

    private String[] confNames;

    private Integer confType;

    private String useBy;

    private String[] useByNames;

    private String createTime;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String titleList;

    public String getTitleList() {
        return titleList;
    }

    public void setTitleList(String titleList) {
        this.titleList = titleList;
    }

    public Long getConfId() {
        return confId;
    }

    public void setConfId(Long confId) {
        this.confId = confId;
    }

    public String[] getConfNames() {
        return confNames;
    }

    public void setConfNames(String[] confNames) {
        this.confNames = confNames;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public Integer getConfType() {
        return confType;
    }

    public void setConfType(Integer confType) {
        this.confType = confType;
    }

    public String getUseBy() {
        return useBy;
    }

    public void setUseBy(String useBy) {
        this.useBy = useBy;
    }

    public String[] getUseByNames() {
        return useByNames;
    }

    public void setUseByNames(String[] useByNames) {
        this.useByNames = useByNames;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

}
