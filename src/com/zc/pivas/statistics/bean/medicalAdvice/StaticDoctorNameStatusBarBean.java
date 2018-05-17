package com.zc.pivas.statistics.bean.medicalAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 按批次->医生+状态进行统计
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorNameStatusBarBean {
    /**
     * 医生名称列表
     */
    private List<String> doctorNameList = new ArrayList<String>();

    /**
     * 医嘱状态 -> 医生列表
     */
    private List<StaticDoctorStatus2NameBean> status2DoctorList = new ArrayList<StaticDoctorStatus2NameBean>();

    public List<String> getDoctorNameList() {
        return doctorNameList;
    }

    public void setDoctorNameList(List<String> doctorNameList) {
        this.doctorNameList = doctorNameList;
    }

    public void addDoctorNameList(String doctorName) {
        this.doctorNameList.add(doctorName);
    }

    public List<StaticDoctorStatus2NameBean> getStatus2DoctorList() {
        return status2DoctorList;
    }

    public void setStatus2DoctorList(List<StaticDoctorStatus2NameBean> status2DoctorList) {
        this.status2DoctorList = status2DoctorList;
    }

    public void addStatus2DoctorList(StaticDoctorStatus2NameBean status2Doctor) {
        this.status2DoctorList.add(status2Doctor);
    }

}
