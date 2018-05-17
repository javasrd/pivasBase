package com.zc.pivas.statistics.bean.prescription;

import java.util.Arrays;
import java.util.List;

/**
 * 药单统计 搜索条件
 *
 * @author jagger
 * @version 1.0
 */
public class PrescriptionSearchBean {
    /**
     * 搜索开始时间
     */
    private String searchStartTime;

    /**
     * 搜索结束时间
     */
    private String searchEndTime;

    private String searchTime;

    /**
     * 批次列表
     */
    private String searchBatchs;

    /**
     * 病区列表
     */
    private String searchDeptNames;

    /**
     * 药品分类
     */
    private String medicCategorys;

    /**
     * 药品标签
     */
    private String medicLabels;

    /**
     * 药单状态过滤条件
     */
    private String singleState;

    /**
     * 是否是最近三个月的数据1：是 0：否
     */
    private String compareResult;

    /**
     * 医生列表
     */
    private String searchDoctorIds;

    /**
     * 医生
     */
    private String searchDoctorId;

    /**
     * 病区名称
     */
    private String deptName;

    public List<String> getMedicCategorys() {
        if (medicCategorys == null || "".equals(medicCategorys)) {
            return null;
        }
        String[] batchItem = medicCategorys.split(",");
        return Arrays.asList(batchItem);
    }

    public void setMedicCategorys(String medicCategorys) {
        this.medicCategorys = medicCategorys;
    }

    public List<String> getMedicLabels() {
        if (medicLabels == null || "".equals(medicLabels)) {
            return null;
        }
        String[] batchItem = medicLabels.split(",");
        return Arrays.asList(batchItem);
    }

    public void setMedicLabels(String medicLabels) {
        this.medicLabels = medicLabels;
    }

    public String getSearchDoctorId() {
        return searchDoctorId;
    }

    public void setSearchDoctorId(String searchDoctorId) {
        this.searchDoctorId = searchDoctorId;
    }

    public void setSearchDoctorIds(String searchDoctorIds) {
        this.searchDoctorIds = searchDoctorIds;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCompareResult() {
        return compareResult;
    }

    public void setCompareResult(String compareResult) {
        this.compareResult = compareResult;
    }

    private Integer batchID;

    public Integer getBatchID() {
        return batchID;
    }

    public void setBatchID(Integer batchID) {
        this.batchID = batchID;
    }

    public String getSingleState() {
        return singleState;
    }

    public void setSingleState(String singleState) {
        this.singleState = singleState;
    }

    public String getSearchStartTime() {
        return searchStartTime;
    }

    public void setSearchStartTime(String searchStartTime) {
        this.searchStartTime = searchStartTime;
    }

    public String getSearchEndTime() {
        return searchEndTime;
    }

    public void setSearchEndTime(String searchEndTime) {
        this.searchEndTime = searchEndTime;
    }

    public List<String> getSearchBatchs() {
        if (searchBatchs == null || "".equals(searchBatchs)) {
            return null;
        }
        String[] batchItem = searchBatchs.split(",");
        return Arrays.asList(batchItem);

    }

    public List<String> getSearchDoctorIds() {
        if (searchDoctorIds == null || "".equals(searchDoctorIds)) {
            return null;
        }
        String[] searchDoctorId = searchDoctorIds.split(",");
        return Arrays.asList(searchDoctorId);

    }

    public void setSearchBatchs(String searchBatchs) {
        this.searchBatchs = searchBatchs;
    }

    public List<String> getSearchDeptNames() {
        if (searchDeptNames == null || "".equals(searchDeptNames)) {
            return null;
        }
        String[] deptNameItem = searchDeptNames.split(",");
        return Arrays.asList(deptNameItem);
    }

    public void setSearchDeptNames(String searchDeptNames) {
        this.searchDeptNames = searchDeptNames;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

}
