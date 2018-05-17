package com.zc.pivas.statistics.bean.configFee;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置费报表 批次名称bean
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeBatchStatusBarBean {
    /**
     * 批次名称列表
     */
    private List<String> batchNameList = new ArrayList<String>();

    private List<ConfigFeeStatus2BatchBean> status2BatchList = new ArrayList<ConfigFeeStatus2BatchBean>();

    public List<String> getBatchNameList() {
        return batchNameList;
    }

    public void setBatchNameList(List<String> batchNameList) {
        this.batchNameList = batchNameList;
    }

    public void addBatchNameList(String batchName) {
        this.batchNameList.add(batchName);
    }

    public List<ConfigFeeStatus2BatchBean> getStatus2BatchList() {
        return status2BatchList;
    }

    public void setStatus2BatchList(List<ConfigFeeStatus2BatchBean> status2BatchList) {
        this.status2BatchList = status2BatchList;
    }

    public void addStatus2BatchList(ConfigFeeStatus2BatchBean status2Batch) {
        this.status2BatchList.add(status2Batch);
    }

}
