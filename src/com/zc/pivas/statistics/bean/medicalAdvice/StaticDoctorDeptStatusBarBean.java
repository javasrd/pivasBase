package com.zc.pivas.statistics.bean.medicalAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 按病区和状态统计
 *
 * @author jagger
 * @version 1.0
 */
public class StaticDoctorDeptStatusBarBean {
    /**
     * 病区名称列表
     */
    private List<String> deptNameList = new ArrayList<String>();

    /**
     * 医嘱状态 -> 医生状态 统计
     */
    private List<StaticDoctorStatus2DeptBean> status2DeptList = new ArrayList<StaticDoctorStatus2DeptBean>();

    public List<String> getDeptNameList() {
        return deptNameList;
    }

    public void setDeptNameList(List<String> deptNameList) {
        this.deptNameList = deptNameList;
    }

    public void addDeptNameList(String deptName) {
        this.deptNameList.add(deptName);
    }

    public List<StaticDoctorStatus2DeptBean> getStatus2DeptList() {
        return status2DeptList;
    }

    public void setStatus2DeptList(List<StaticDoctorStatus2DeptBean> status2DeptList) {
        this.status2DeptList = status2DeptList;
    }

    public void setStatus2DeptList(StaticDoctorStatus2DeptBean status2Dept) {
        this.status2DeptList.add(status2Dept);
    }

}
