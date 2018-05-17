package com.zc.pivas.statistics.bean.prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * 按批次->批次搜索条件
 *
 * @author jagger
 * @version 1.0
 */
public class BatchStatusBarBean {
    /**
     * 批次名称列表
     */
    private List<String> batchNameList = new ArrayList<String>();

    private List<Status2BatchBean> status2BatchList = new ArrayList<Status2BatchBean>();

    public List<String> getBatchNameList() {
        return batchNameList;
    }

    public void setBatchNameList(List<String> batchNameList) {
        this.batchNameList = batchNameList;
    }

    public void addBatchNameList(String batchName) {
        this.batchNameList.add(batchName);
    }

    public List<Status2BatchBean> getStatus2BatchList() {
        return status2BatchList;
    }

    public void setStatus2BatchList(List<Status2BatchBean> status2BatchList) {
        this.status2BatchList = status2BatchList;
    }

    public void addStatus2BatchList(Status2BatchBean status2Batch) {
        this.status2BatchList.add(status2Batch);
    }

}
