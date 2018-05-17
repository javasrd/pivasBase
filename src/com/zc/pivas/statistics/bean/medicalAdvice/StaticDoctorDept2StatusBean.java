package com.zc.pivas.statistics.bean.medicalAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 按病区和医嘱状态统计
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorDept2StatusBean {

    /**
     * 病区名称
     */
    private String deptName;

    /**
     * 医嘱状态统计列表
     */
    private List<StaticDoctorStatusCount> dctrStatusCountList = new ArrayList<StaticDoctorStatusCount>();

    public List<StaticDoctorStatusCount> getDctrStatusCountList() {
        return dctrStatusCountList;
    }

    public void setDctrStatusCountList(List<StaticDoctorStatusCount> dctrStatusCountList) {
        this.dctrStatusCountList = dctrStatusCountList;
    }

    public void addDctrStatusCountList(StaticDoctorStatusCount dctrStatusCount) {
        this.dctrStatusCountList.add(dctrStatusCount);
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
