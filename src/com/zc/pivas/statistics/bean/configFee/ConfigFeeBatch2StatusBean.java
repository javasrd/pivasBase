package com.zc.pivas.statistics.bean.configFee;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置费报表 批次状态bean
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeBatch2StatusBean {

    /**
     * 批次
     */
    private Integer zxbc;

    /**
     * 批次名称
     */
    private String batchNAME;

    /**
     * 药单状态统计列表
     */
    private List<ConfigFeeStatusCount> cfStatusCountList = new ArrayList<ConfigFeeStatusCount>();

    public Integer getZxbc() {
        return zxbc;
    }

    public void setZxbc(Integer zxbc) {
        this.zxbc = zxbc;
    }

    public String getBatchNAME() {
        return batchNAME;
    }

    public void setBatchNAME(String batchNAME) {
        this.batchNAME = batchNAME;
    }

    public List<ConfigFeeStatusCount> getCfStatusCountList() {
        return cfStatusCountList;
    }

    public void setCfStatusCountList(List<ConfigFeeStatusCount> ydStatusCountList) {
        this.cfStatusCountList = ydStatusCountList;
    }

    public void addCfStatusCountList(ConfigFeeStatusCount ydStatusCount) {
        this.cfStatusCountList.add(ydStatusCount);
    }

}
