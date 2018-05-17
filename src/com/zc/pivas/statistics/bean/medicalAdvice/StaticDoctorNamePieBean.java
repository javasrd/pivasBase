package com.zc.pivas.statistics.bean.medicalAdvice;

/**
 * 按医生进行统计
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorNamePieBean {
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
