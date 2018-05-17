package com.zc.pivas.statistics.bean.medicalAdvice;

/**
 * 审方错误类型
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorType {
    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 审方错误类型
     */
    private Integer statusKey;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

}
