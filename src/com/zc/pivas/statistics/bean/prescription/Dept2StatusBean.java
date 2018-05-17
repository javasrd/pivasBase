package com.zc.pivas.statistics.bean.prescription;

import java.util.ArrayList;
import java.util.List;

/**
 * 按病区状态进行统计
 * <功能详细描述>
 *
 * @author jagger
 * @version 1.0
 */
public class Dept2StatusBean {

    /**
     * 病区名称
     */
    private String deptName;

    /**
     * 药单状态统计列表
     */
    private List<YDStatusCount> ydStatusCountList = new ArrayList<YDStatusCount>();

    public List<YDStatusCount> getYdStatusCountList() {
        return ydStatusCountList;
    }

    public void setYdStatusCountList(List<YDStatusCount> ydStatusCountList) {
        this.ydStatusCountList = ydStatusCountList;
    }

    public void addYdStatusCountList(YDStatusCount ydStatusCount) {
        this.ydStatusCountList.add(ydStatusCount);
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
