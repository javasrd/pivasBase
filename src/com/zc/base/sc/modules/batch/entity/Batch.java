package com.zc.base.sc.modules.batch.entity;

import com.zc.base.common.dao.AbstractDO;

import java.util.Arrays;

/**
 * 批次实体类
 *
 * @author Ray
 * @version 1.0
 */
public class Batch extends AbstractDO {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String num;
    private String name;
    private String[] names;
    private Integer isEmptyBatch;
    private String startTime;
    private String endTime;
    private Integer isSecendAdvice;
    private String color;
    private Integer enabled;
    private String reserve1;
    private String reserve2;
    private String reserve3;
    private Integer is0P;
    private String timeType;
    private Integer orderNum;
    private String isForce;
    private String isVolume;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = (num == null ? null : num.trim());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = (name == null ? null : name.trim());
    }

    public Integer getIsEmptyBatch() {
        return this.isEmptyBatch;
    }

    public void setIsEmptyBatch(Integer isEmptyBatch) {
        this.isEmptyBatch = isEmptyBatch;
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

    public Integer getIsSecendAdvice() {
        return this.isSecendAdvice;
    }

    public void setIsSecendAdvice(Integer isSecendAdvice) {
        this.isSecendAdvice = isSecendAdvice;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = (color == null ? null : color.trim());
    }

    public Integer getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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

    public String[] getNames() {
        return this.names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public Integer getIs0P() {
        return this.is0P;
    }

    public void setIs0P(Integer is0p) {
        this.is0P = is0p;
    }

    public String toString() {
        return "Batch [id=" + this.id + ", num=" + this.num + ", name=" + this.name + ", names=" + Arrays.toString(this.names) + ", isEmptyBatch=" + this.isEmptyBatch + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", isSecendAdvice=" + this.isSecendAdvice + ", color=" + this.color + ", enabled=" + this.enabled + ", reserve1=" + this.reserve1 + ", reserve2=" + this.reserve2 + ", reserve3=" + this.reserve3 + ", is0p=" + this.is0P + ", orderNum=" + this.orderNum + ",timeType=" + this.timeType + "]";
    }

    public String getTimeType() {
        return this.timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getIsForce() {
        return this.isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getIsVolume() {
        return this.isVolume;
    }

    public void setIsVolume(String isVolume) {
        this.isVolume = isVolume;
    }
}
