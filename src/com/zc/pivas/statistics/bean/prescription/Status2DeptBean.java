package com.zc.pivas.statistics.bean.prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * 按药单状态病区统计
 *
 * @author jagger
 * @version 1.0
 */
public class Status2DeptBean {
    /**
     * 状态 0执行中 1停止
     */
    private Integer statusKey;

    /**
     * 批次药单数量列表
     */
    private List<Integer> ydDeptCountList = new ArrayList<Integer>();

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public List<Integer> getYdDeptCountList() {
        return ydDeptCountList;
    }

    public void setYdDeptCountList(List<Integer> ydDeptCountList) {
        this.ydDeptCountList = ydDeptCountList;
    }

    public void addYdDeptCountList(Integer ydDeptCount) {
        this.ydDeptCountList.add(ydDeptCount);
    }

}
