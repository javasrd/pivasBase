package com.zc.base.sc.modules.medicalFrequency.entity;

import com.zc.base.common.dao.AbstractDO;

import java.util.Arrays;

public class MedicalFrequency extends AbstractDO {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;
    private String[] codes;
    private String name;
    private String[] names;
    private Integer interval;
    private Integer timeOfDay;
    private String startTime;
    private String endTime;
    private String reserve1;
    private String reserve2;
    private String reserve3;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = (code == null ? null : code.trim());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = (name == null ? null : name.trim());
    }

    public Integer getInterval() {
        return this.interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getTimeOfDay() {
        return this.timeOfDay;
    }

    public void setTimeOfDay(Integer timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReserve1() {
        return this.reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = (reserve1 == null ? null : reserve1.trim());
    }

    public String getReserve2() {
        return this.reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = (reserve2 == null ? null : reserve2.trim());
    }

    public String getReserve3() {
        return this.reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = (reserve3 == null ? null : reserve3.trim());
    }

    public String[] getCodes() {
        return this.codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }

    public String[] getNames() {
        return this.names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }


    public String toString() {
        return "MedicalFrequency [id=" + this.id + ", code=" + this.code + ", codes=" + Arrays.toString(this.codes) + ", name=" + this.name + ", names=" + Arrays.toString(this.names) + ", interval=" + this.interval + ", timeOfDay=" + this.timeOfDay + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", reserve1=" + this.reserve1 + ", reserve2=" + this.reserve2 + ", reserve3=" + this.reserve3 + "]";
    }
}
