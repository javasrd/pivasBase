package com.zc.pivas.medicamentslabel.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 药品标签实体类
 *
 * @author kunkka
 * @version 1.0
 */
public class MedicLabel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标签id
     */
    private Long labelId;

    /**
     * 标签名称
     */
    private String labelName;

    private String[] labelNames;

    private String labelNo;

    /**
     * 是否可用（0：显示，1：否）
     */
    private int isActive;

    /**
     * 是否空包（0：不是空包，1：是空包）
     */
    private int isNull;

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }

    private String isActiveResult;

    public String[] getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String[] labelNames) {
        this.labelNames = labelNames;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getIsActiveResult() {
        return isActiveResult;
    }

    public void setIsActiveResult(String isActiveResult) {
        this.isActiveResult = isActiveResult;
    }

    public int getIsNull() {
        return isNull;
    }

    public void setIsNull(int isNull) {
        this.isNull = isNull;
    }

    @Override
    public String toString() {
        return "MedicLabel [labelId=" + labelId + ", labelName=" + labelName + ", labelNames="
                + Arrays.toString(labelNames) + ", isActive=" + isActive + ", isActiveResult=" + isActiveResult + "]";
    }

}
