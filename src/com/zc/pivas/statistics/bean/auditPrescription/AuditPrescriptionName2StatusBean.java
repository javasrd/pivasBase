package com.zc.pivas.statistics.bean.auditPrescription;

import java.util.ArrayList;
import java.util.List;

/**
 * 审方工作量统计Bean(医师、医嘱状态)
 *
 * @author jagger
 * @version 1.0
 */
public class AuditPrescriptionName2StatusBean {

    /**
     * 医生工号
     */
    private String doctorId;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 医嘱状态统计列表
     */
    private List<AuditPrescriptionStatusCount> dctrStatusCountList = new ArrayList<AuditPrescriptionStatusCount>();

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

    public List<AuditPrescriptionStatusCount> getDctrStatusCountList() {
        return dctrStatusCountList;
    }

    public void setDctrStatusCountList(List<AuditPrescriptionStatusCount> dctrStatusCountList) {
        this.dctrStatusCountList = dctrStatusCountList;
    }

    public void addDctrStatusCountList(AuditPrescriptionStatusCount dctrStatusCount) {
        this.dctrStatusCountList.add(dctrStatusCount);
    }

}
