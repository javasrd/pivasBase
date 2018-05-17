package com.zc.pivas.statistics.bean.auditPrescription;

import java.util.ArrayList;
import java.util.List;

/**
 * 审方工作量统计Bean(医生名称列表、医嘱状态)
 *
 * @author jagger
 * @version 1.0
 */
public class AuditPrescriptionNameStatusBarBean {
    /**
     * 医生名称列表
     */
    private List<String> doctorNameList = new ArrayList<String>();

    /**
     * 医嘱状态 -> 医生列表
     */
    private List<AuditPrescriptionStatus2NameBean> status2DoctorList = new ArrayList<AuditPrescriptionStatus2NameBean>();

    public List<String> getDoctorNameList() {
        return doctorNameList;
    }

    public void setDoctorNameList(List<String> doctorNameList) {
        this.doctorNameList = doctorNameList;
    }

    public void addDoctorNameList(String doctorName) {
        this.doctorNameList.add(doctorName);
    }

    public List<AuditPrescriptionStatus2NameBean> getStatus2DoctorList() {
        return status2DoctorList;
    }

    public void setStatus2DoctorList(List<AuditPrescriptionStatus2NameBean> status2DoctorList) {
        this.status2DoctorList = status2DoctorList;
    }

    public void addStatus2DoctorList(AuditPrescriptionStatus2NameBean status2Doctor) {
        this.status2DoctorList.add(status2Doctor);
    }

}
