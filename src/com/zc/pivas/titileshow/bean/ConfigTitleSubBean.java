package com.zc.pivas.titileshow.bean;

import java.io.Serializable;

/**
 * @author kunkka
 * @version 1.0
 */
public class ConfigTitleSubBean implements Serializable {

    private static final long serialVersionUID = 1L;


    public ConfigTitleSubBean() {
        super();
    }

    public ConfigTitleSubBean(Long confId, String field, Integer priority) {
        super();
        this.confId = confId;
        this.field = field;
        this.priority = priority;
    }

    private Long confId;

    private String field;

    private Integer priority;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    public Long getConfId() {
        return confId;
    }

    public void setConfId(Long confId) {
        this.confId = confId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    @Override
    public String toString() {
        return "ConfigTitleSubBean [confId=" + confId + ", field=" + field + ", priority=" + priority
                + ", reserve1=" + reserve1 + ", reserve2=" + reserve2 + ", reserve3=" + reserve3 + "]";
    }

}
