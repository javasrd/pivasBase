package com.zc.pivas.statistics.bean.medicalAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 按医生和医嘱状态统计
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorName2StatusBean {

    /**
     * 医生工号
     */
    private Integer doctorId;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 医嘱状态统计列表
     */
    private List<StaticDoctorStatusCount> dctrStatusCountList = new ArrayList<StaticDoctorStatusCount>();

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public List<StaticDoctorStatusCount> getDctrStatusCountList() {
        return dctrStatusCountList;
    }

    public void setDctrStatusCountList(List<StaticDoctorStatusCount> dctrStatusCountList) {
        this.dctrStatusCountList = dctrStatusCountList;
    }

    public void addDctrStatusCountList(StaticDoctorStatusCount dctrStatusCount) {
        this.dctrStatusCountList.add(dctrStatusCount);
    }

}
