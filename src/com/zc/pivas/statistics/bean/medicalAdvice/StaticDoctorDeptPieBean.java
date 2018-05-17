package com.zc.pivas.statistics.bean.medicalAdvice;

/**
 * 按病区进行统计
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorDeptPieBean {
    /**
     * 名称
     */
    private String name;

    /**
     * 统计数量
     */
    private Integer value;

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

}
