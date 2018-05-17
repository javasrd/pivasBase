package com.zc.pivas.statistics.bean.prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * 按批次->病区进行统计
 *
 * @author jagger
 * @version 1.0
 */
public class DeptStatusBarBean {
    /**
     * 批次名称列表
     */
    private List<String> deptNameList = new ArrayList<String>();

    /**
     * 药单状态 -> 批次 统计
     */
    private List<Status2DeptBean> status2DeptList = new ArrayList<Status2DeptBean>();

    public List<String> getDeptNameList() {
        return deptNameList;
    }

    public void setDeptNameList(List<String> deptNameList) {
        this.deptNameList = deptNameList;
    }

    public void addDeptNameList(String deptName) {
        this.deptNameList.add(deptName);
    }

    public List<Status2DeptBean> getStatus2DeptList() {
        return status2DeptList;
    }

    public void setStatus2DeptList(List<Status2DeptBean> status2DeptList) {
        this.status2DeptList = status2DeptList;
    }

    public void setStatus2DeptList(Status2DeptBean status2Dept) {
        this.status2DeptList.add(status2Dept);
    }

}
