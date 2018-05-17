package com.zc.pivas.statistics.bean.druguse;

import java.io.Serializable;

/**
 * group by批次：药物统计柱状图
 *
 * @author cacabin
 * @version 1.0
 */
public class DrugUseBarGroupByBatch implements Serializable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 批次名称
     */
    private String batchNAME;

    /**
     * 药品分类名称
     */
    private String drugClassName;

    /**
     * 统计数量
     */
    private int ststsCount;

    /**
     * 统计金额
     */
    private double ststsCost;

    /**
     * 执行批次号
     */
    private int zxbc;

    public int getZxbc() {
        return zxbc;
    }

    public void setZxbc(int zxbc) {
        this.zxbc = zxbc;
    }

    public String getBatchNAME() {
        return batchNAME;
    }

    public void setBatchNAME(String batchNAME) {
        this.batchNAME = batchNAME;
    }

    public String getDrugClassName() {
        return drugClassName == null ? "###" : drugClassName;
    }

    public void setDrugClassName(String drugClassName) {
        this.drugClassName = drugClassName;
    }

    public int getStstsCount() {
        return ststsCount;
    }

    public void setStstsCount(int ststsCount) {
        this.ststsCount = ststsCount;
    }

    public double getStstsCost() {
        return ststsCost;
    }

    public void setStstsCost(double ststsCost) {
        this.ststsCost = ststsCost;
    }

    @Override
    public String toString() {
        return "DrugUseBarGroupByBatch [batchNAME=" + batchNAME + ", drugClassName=" + drugClassName + ", ststsCount="
                + ststsCount + ",ststsCost=" + ststsCost + "]";
    }

}
