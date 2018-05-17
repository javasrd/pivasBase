package com.zc.pivas.statistics.bean.medicalAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 按医嘱状态->医生统计
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorStatus2NameBean {
    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 审方错误类型
     */
    private Integer statusKey;

    /**
     * 医嘱数量列表
     */
    private List<Integer> doctorNameCountList = new ArrayList<Integer>();

    public List<Integer> getDoctorNameCountList() {
        return doctorNameCountList;
    }

    public void setDoctorNameCountList(List<Integer> doctorNameCountList) {
        this.doctorNameCountList = doctorNameCountList;
    }

    public void addDoctorNameCountList(Integer doctorNameCount) {
        this.doctorNameCountList.add(doctorNameCount);
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

}
