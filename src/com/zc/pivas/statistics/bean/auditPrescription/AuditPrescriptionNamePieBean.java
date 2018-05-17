package com.zc.pivas.statistics.bean.auditPrescription;

/**
 * 审方工作量统计Bean(医生名称、统计数量)
 *
 * @author jagger
 * @version 1.0
 */
public class AuditPrescriptionNamePieBean {
    /**
     * 医生名称
     */
    private String name;

    /**
     * 统计数量
     */
    private Integer value;

    /**
     * 医生ID
     */
    private String doctorId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

}
