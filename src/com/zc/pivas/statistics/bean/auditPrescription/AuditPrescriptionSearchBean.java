package com.zc.pivas.statistics.bean.auditPrescription;

import java.util.Arrays;
import java.util.List;


/**
 * 审方工作量统计查询条件Bean(搜索时间、医生列表)
 *
 * @author jagger
 * @version 1.0
 */
public class AuditPrescriptionSearchBean {
    /**
     * 搜索时间
     */
    private String searchTime;

    /**
     * 医生列表
     */
    private String searchDoctorIds;

    private String doctorID;

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public List<String> getSearchDoctorIds() {
        if (searchDoctorIds == null || "".equals(searchDoctorIds)) {
            return null;
        }
        String[] doctorIdItem = searchDoctorIds.split(",");
        return Arrays.asList(doctorIdItem);

    }

    public void setSearchDoctorIds(String searchDoctorIds) {
        this.searchDoctorIds = searchDoctorIds;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

}
