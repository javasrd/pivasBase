package com.zc.pivas.statistics.bean.auditPrescription;

/**
 * 审方工作量统计Bean(医生名称、统计数量、状态名称、审方错误类型、病区名称)
 *
 * @author jagger
 * @version 1.0
 */
public class AuditPrescriptionStatusBean {
    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 统计数量
     */
    private Integer ststsCount;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 审方错误类型
     */
    private Integer statusKey;

    /**
     * 医生ID
     */
    private String doctorId;

    /**
     * 病区名称
     */
    private String deptName;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getStstsCount() {
        return ststsCount;
    }

    public void setStstsCount(Integer ststsCount) {
        this.ststsCount = ststsCount;
    }

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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
