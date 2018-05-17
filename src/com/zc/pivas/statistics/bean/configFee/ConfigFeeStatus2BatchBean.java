package com.zc.pivas.statistics.bean.configFee;

import java.util.ArrayList;
import java.util.List;

/**
 * 按药单状态->批次统计
 * <功能详细描述>
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeStatus2BatchBean {
    /**
     * 配置费的类别，0:普通药物 1:抗生素 2:细胞毒药物 3:TPN
     */
    private Integer statusKey;

    /**
     * 批次药单数量列表
     */
    private List<Float> cfBatchCountList = new ArrayList<Float>();

    public Integer getStatusKey() {
        return statusKey;
    }

    public List<Float> getCfBatchCountList() {
        return cfBatchCountList;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public void setCfBatchCountList(List<Float> ydBatchCountList) {
        this.cfBatchCountList = ydBatchCountList;
    }

    public void addCfBatchCountList(Float batchCount) {
        this.cfBatchCountList.add(batchCount);
    }

}
