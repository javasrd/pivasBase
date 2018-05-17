package com.zc.pivas.statistics.bean.configFee;

import java.util.ArrayList;
import java.util.List;

/**
 * 按配置费的类别->批次统计
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeStatus2DeptBean {
    /**
     * 配置费的类别，0:普通药物 1:抗生素 2:细胞毒药物 3:TPN
     */
    private Integer statusKey;

    /**
     * 批次药单数量列表
     */
    private List<Float> cfDeptCountList = new ArrayList<Float>();

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public List<Float> getCfDeptCountList() {
        return cfDeptCountList;
    }

    public void setCfDeptCountList(List<Float> ydDeptCountList) {
        this.cfDeptCountList = ydDeptCountList;
    }

    public void addCfDeptCountList(Float ydDeptCount) {
        this.cfDeptCountList.add(ydDeptCount);
    }

}
