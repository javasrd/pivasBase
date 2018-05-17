package com.zc.pivas.statistics.bean.medicalAdvice;

/**
 * 药单统计数量
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorStatusCount {
    /**
     * 统计数量
     */
    private Integer ststsCount;

    /**
     * 审方错误类型
     */
    private Integer statusKey;

    /**
     * 状态名称
     */
    private String statusName;

    public Integer getStstsCount() {
        return ststsCount;
    }

    public void setStstsCount(Integer ststsCount) {
        this.ststsCount = ststsCount;
    }

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
