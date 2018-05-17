package com.zc.pivas.statistics.bean.medicalAdvice;

import java.util.Arrays;
import java.util.List;

/**
 * 按医嘱状态->搜索条件
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorSearchBean {
    /**
     * 搜索时间
     */
    private String searchTime;

    /**
     * 医生列表
     */
    private String searchDoctorIds;

    /**
     * 病区列表
     */
    private String searchDeptNames;

    private String doctorID;

    /**
     * 是否是最近三个月的数据1：是 0：否
     */
    private String compareResult;

    private String deptName;

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getCompareResult() {
        return compareResult;
    }

    public void setCompareResult(String compareResult) {
        this.compareResult = compareResult;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

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
        //List<String> aa = new ArrayList<String>(batchItem);
        return Arrays.asList(doctorIdItem);

    }

    public void setSearchDoctorIds(String searchDoctorIds) {
        this.searchDoctorIds = searchDoctorIds;
    }

    public List<String> getSearchDeptNames() {
        if (searchDeptNames == null || "".equals(searchDeptNames)) {
            return null;
        }
        String[] deptNameItem = searchDeptNames.split(",");
        //List<String> aa = new ArrayList<String>(batchItem);
        return Arrays.asList(deptNameItem);
    }

    public void setSearchDeptNames(String searchDeptNames) {
        this.searchDeptNames = searchDeptNames;
    }

}
