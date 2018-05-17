package com.zc.pivas.statistics.bean.medicalAdvice;

/**
 * 按医师统计bean
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorNameBean {
    /**
     * 医生Id
     */
    private String doctorId;

    /**
     * 医生名称
     */
    private String doctorName;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

}
