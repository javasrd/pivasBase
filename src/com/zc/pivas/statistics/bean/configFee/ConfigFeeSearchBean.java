package com.zc.pivas.statistics.bean.configFee;

import java.util.Arrays;
import java.util.List;

/**
 * 配置费搜索条件类
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeSearchBean {
    /**
     * 搜索时间
     */
    private String searchTime;

    /**
     * 批次列表
     */
    private String searchBatchs;

    /**
     * 病区列表
     */
    private String searchDeptNames;

    private String batchID;

    private String deptName;

    /**
     * 是否是最近三个月的数据1：是 0：否
     */
    private String compareResult;

    public String getCompareResult() {
        return compareResult;
    }

    public void setCompareResult(String compareResult) {
        this.compareResult = compareResult;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
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

    public List<String> getSearchBatchs() {
        if (searchBatchs == null || "".equals(searchBatchs)) {
            return null;
        }
        String[] batchItem = searchBatchs.split(",");
        //List<String> aa = new ArrayList<String>(batchItem);
        return Arrays.asList(batchItem);

    }

    public void setSearchBatchs(String searchBatchs) {
        this.searchBatchs = searchBatchs;
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
