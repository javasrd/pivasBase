package com.zc.pivas.statistics.bean.prescription;

/**
 * 药单统计数量
 * <功能详细描述>
 *
 * @author jagger
 * @version 1.0
 */
public class YDStatusCount {
    /**
     * 统计数量
     */
    private Integer ststsCount;

    /**
     * 状态 0执行中 1停止
     */
    private Integer statusKey;

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

}
