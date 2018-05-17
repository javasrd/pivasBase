package com.zc.base.sc.modules.generalRule.entity;

public class RuleRefBatch {
    private Long ruleId;
    private Long serialNumber;
    private Long batchId;
    private Short isFixedBatch;

    public Long getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getBatchId() {
        return this.batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Short getIsFixedBatch() {
        return this.isFixedBatch;
    }

    public void setIsFixedBatch(Short isFixedBatch) {
        this.isFixedBatch = isFixedBatch;
    }
}
