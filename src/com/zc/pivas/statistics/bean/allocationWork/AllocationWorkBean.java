package com.zc.pivas.statistics.bean.allocationWork;

import java.io.Serializable;

/**
 * 配置工作量统计
 *
 * @author jagger
 * @version 1.0
 */
public class AllocationWorkBean implements Serializable {

    private static final long serialVersionUID = 4900806357651132131L;
    /**
     * 配置人名称
     */
    private String workName;

    /**
     * 配置总数
     */
    private Integer totalCount;

    /**
     * 普通药
     */
    private Integer generalCount;

    /***
     * 抗生素
     */
    private Integer antibioticCount;

    /**
     * 化疗药
     */
    private Integer chemotherapyCount;

    /**
     * 营养药
     */
    private Integer nutritionCount;

    /**
     * 工作量系数汇总
     */
    private Double workload;

    private String workloadStr;

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getGeneralCount() {
        return generalCount;
    }

    public void setGeneralCount(Integer generalCount) {
        this.generalCount = generalCount;
    }

    public Integer getAntibioticCount() {
        return antibioticCount;
    }

    public void setAntibioticCount(Integer antibioticCount) {
        this.antibioticCount = antibioticCount;
    }

    public Integer getChemotherapyCount() {
        return chemotherapyCount;
    }

    public void setChemotherapyCount(Integer chemotherapyCount) {
        this.chemotherapyCount = chemotherapyCount;
    }

    public Integer getNutritionCount() {
        return nutritionCount;
    }

    public void setNutritionCount(Integer nutritionCount) {
        this.nutritionCount = nutritionCount;
    }

    public Double getWorkload() {
        return workload;
    }

    public void setWorkload(Double workload) {
        this.workload = workload;
    }

    public String getWorkloadStr() {
        return workloadStr;
    }

    public void setWorkloadStr(String workloadStr) {
        this.workloadStr = workloadStr;
    }

}
