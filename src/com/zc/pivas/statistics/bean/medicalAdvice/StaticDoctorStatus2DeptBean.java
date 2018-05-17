package com.zc.pivas.statistics.bean.medicalAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生统计
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorStatus2DeptBean {
    /**
     * 审方错误类型
     */
    private Integer statusKey;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 医生对应医嘱数量列表
     */
    private List<Integer> dctrDeptCountList = new ArrayList<Integer>();

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public List<Integer> getDctrDeptCountList() {
        return dctrDeptCountList;
    }

    public void setDctrDeptCountList(List<Integer> dctrDeptCountList) {
        this.dctrDeptCountList = dctrDeptCountList;
    }

    public void addDctrDeptCountList(Integer dctrDeptCount) {
        this.dctrDeptCountList.add(dctrDeptCount);
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
