package com.zc.pivas.statistics.bean.prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * 按批次和状态进行统计
 *
 * @author jagger
 * @version 1.0
 */

public class Batch2StatusBean {

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
    private List<YDStatusCount> ydStatusCountList = new ArrayList<YDStatusCount>();

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

    public List<YDStatusCount> getYdStatusCountList() {
        return ydStatusCountList;
    }

    public void setYdStatusCountList(List<YDStatusCount> ydStatusCountList) {
        this.ydStatusCountList = ydStatusCountList;
    }

    public void addYdStatusCountList(YDStatusCount ydStatusCount) {
        this.ydStatusCountList.add(ydStatusCount);
    }

}
