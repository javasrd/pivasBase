package com.zc.pivas.statistics.bean.prescription;

/**
 * 药单统计 状态查询类
 *
 * @author jagger
 * @version 1.0
 */
public class PrescriptionStatusBean {
    /**
     * 批次名称
     */
    private String batchNAME;

    /**
     * 统计数量
     */
    private Integer ststsCount;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 配置费的类别，0:普通药物 1:抗生素 2:细胞毒药物 3:TPN
     */
    private Integer statusKey;

    /**
     * 批次
     */
    private Integer zxbc;

    /**
     * 病区名称
     */
    private String deptName;

    public String getBatchNAME() {
        return batchNAME;
    }

    public void setBatchNAME(String batchNAME) {
        this.batchNAME = batchNAME;
    }

    public Integer getStstsCount() {
        return ststsCount;
    }

    public void setStstsCount(Integer ststsCount) {
        this.ststsCount = ststsCount;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public Integer getZxbc() {
        return zxbc;
    }

    public void setZxbc(Integer zxbc) {
        this.zxbc = zxbc;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
