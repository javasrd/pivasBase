package com.zc.pivas.statistics.bean.prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * 按药单状态->批次统计
 *
 * @author jagger
 * @version 1.0
 */
public class Status2BatchBean {
    /**
     * 状态 0执行中 1停止
     */
    private Integer statusKey;

    /**
     * 批次药单数量列表
     */
    private List<Integer> ydBatchCountList = new ArrayList<Integer>();

    public Integer getStatusKey() {
        return statusKey;
    }

    public List<Integer> getYdBatchCountList() {
        return ydBatchCountList;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public void setYdBatchCountList(List<Integer> ydBatchCountList) {
        this.ydBatchCountList = ydBatchCountList;
    }

    public void addYdBatchCountList(Integer batchCount) {
        this.ydBatchCountList.add(batchCount);
    }

}
