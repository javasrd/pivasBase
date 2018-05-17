package com.zc.pivas.statistics.bean.configFee;

/**
 * 药单统计数量
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeStatusCount {
    /**
     * 统计数量
     */
    private Float ststsCount;

    /**
     * 状态 0执行中 1停止
     */
    private Integer statusKey;

    public Float getStstsCount() {
        return ststsCount;
    }

    public void setStstsCount(Float ststsCount) {
        this.ststsCount = ststsCount;
    }

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

}
