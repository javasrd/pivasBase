package com.zc.pivas.statistics.bean.configFee;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置费报表 工具栏类
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeDeptStatusBarBean {
    /**
     * 批次名称列表
     */
    private List<String> deptNameList = new ArrayList<String>();

    /**
     * 药单状态 -> 批次 统计
     */
    private List<ConfigFeeStatus2DeptBean> status2DeptList = new ArrayList<ConfigFeeStatus2DeptBean>();

    public List<String> getDeptNameList() {
        return deptNameList;
    }

    public void setDeptNameList(List<String> deptNameList) {
        this.deptNameList = deptNameList;
    }

    public void addDeptNameList(String deptName) {
        this.deptNameList.add(deptName);
    }

    public List<ConfigFeeStatus2DeptBean> getStatus2DeptList() {
        return status2DeptList;
    }

    public void setStatus2DeptList(List<ConfigFeeStatus2DeptBean> status2DeptList) {
        this.status2DeptList = status2DeptList;
    }

    public void setStatus2DeptList(ConfigFeeStatus2DeptBean status2Dept) {
        this.status2DeptList.add(status2Dept);
    }

}
