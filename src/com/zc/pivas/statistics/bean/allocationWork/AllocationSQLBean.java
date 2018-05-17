package com.zc.pivas.statistics.bean.allocationWork;

import java.io.Serializable;

/**
 * 配置工作量SQL Bean
 *
 * @author jagger
 * @version 1.0
 */
public class AllocationSQLBean implements Serializable {

    private static final long serialVersionUID = 6606652217552632354L;
    /**
     * 配置人
     */
    private String workName;

    /**
     * 配置药品分类名
     */
    private String medicCateName;

    /**
     * 数量
     */
    private Integer medicCount;

    /**
     * 工作量系数
     */
    private Double workload;

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getMedicCateName() {
        return medicCateName;
    }

    public void setMedicCateName(String medicCateName) {
        this.medicCateName = medicCateName;
    }

    public Integer getMedicCount() {
        return medicCount;
    }

    public void setMedicCount(Integer medicCount) {
        this.medicCount = medicCount;
    }

    public Double getWorkload() {
        return workload;
    }

    public void setWorkload(Double workload) {
        this.workload = workload;
    }

}
