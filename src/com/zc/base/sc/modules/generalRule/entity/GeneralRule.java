package com.zc.base.sc.modules.generalRule.entity;

import com.zc.base.common.dao.AbstractDO;

import java.util.Arrays;
import java.util.List;

/**
 * 一般规则实体类
 *
 * @author Ray
 * @version 1.0
 */
public class GeneralRule extends AbstractDO {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long medicalFrequencyId;
    private String medicalKeyword;
    private String[] medicalKeywords;
    private String medicalFreqName;
    private Integer refBatchNum;
    private List<RuleRefBatch> ruleRefBatchList;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedicalFrequencyId() {
        return this.medicalFrequencyId;
    }

    public void setMedicalFrequencyId(Long medicalFrequencyId) {
        this.medicalFrequencyId = medicalFrequencyId;
    }

    public String getMedicalKeyword() {
        return this.medicalKeyword;
    }

    public void setMedicalKeyword(String medicalKeyword) {
        this.medicalKeyword = (medicalKeyword == null ? null : medicalKeyword.trim());
    }

    public String getMedicalFreqName() {
        return this.medicalFreqName;
    }

    public void setMedicalFreqName(String medicalFreqName) {
        this.medicalFreqName = medicalFreqName;
    }

    public Integer getRefBatchNum() {
        return this.refBatchNum;
    }

    public void setRefBatchNum(Integer refBatchNum) {
        this.refBatchNum = refBatchNum;
    }

    public List<RuleRefBatch> getRuleRefBatchList() {
        return this.ruleRefBatchList;
    }

    public void setRuleRefBatchList(List<RuleRefBatch> ruleRefBatchList) {
        this.ruleRefBatchList = ruleRefBatchList;
    }

    public String[] getMedicalKeywords() {
        return this.medicalKeywords;
    }

    public void setMedicalKeywords(String[] medicalKeywords) {
        this.medicalKeywords = medicalKeywords;
    }


    public String toString() {
        return "GeneralRule [id=" + this.id + ", medicalFrequencyId=" + this.medicalFrequencyId + ", medicalKeyword=" + this.medicalKeyword + ", medicalKeywords=" + Arrays.toString(this.medicalKeywords) + ", medicalFreqName=" + this.medicalFreqName + ", refBatchNum=" + this.refBatchNum + ", ruleRefBatchList=" + this.ruleRefBatchList + "]";
    }
}
