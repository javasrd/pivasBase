package com.zc.pivas.statistics.bean.auditPrescription;

public class AuditPrescriptionResultType {
    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 审方结果类型
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
