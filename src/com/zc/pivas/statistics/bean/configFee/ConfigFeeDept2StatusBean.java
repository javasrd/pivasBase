package com.zc.pivas.statistics.bean.configFee;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置费报表 病区状态bean
 *
 * @author jagger
 * @version 1.0
 */
public class ConfigFeeDept2StatusBean {

    /**
     * 病区名称
     */
    private String deptName;

    /**
     * 药单状态统计列表
     */
    private List<ConfigFeeStatusCount> cfStatusCountList = new ArrayList<ConfigFeeStatusCount>();

    public List<ConfigFeeStatusCount> getCfStatusCountList() {
        return cfStatusCountList;
    }

    public void setCfStatusCountList(List<ConfigFeeStatusCount> ydStatusCountList) {
        this.cfStatusCountList = ydStatusCountList;
    }

    public void addCfStatusCountList(ConfigFeeStatusCount ydStatusCount) {
        this.cfStatusCountList.add(ydStatusCount);
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
