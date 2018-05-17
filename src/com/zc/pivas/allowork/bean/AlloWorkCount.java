package com.zc.pivas.allowork.bean;

import java.io.Serializable;

/**
 * 对应配置费分类数量
 *
 * @author jagger
 * @version 1.0
 */
public class AlloWorkCount implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String configFeeID;

    /**
     * 分类名称
     */
    private String ruleName;

    /**
     * 统计数量
     */
    private String ststsCount;

    public String getConfigFeeID() {
        return configFeeID;
    }

    public void setConfigFeeID(String configFeeID) {
        this.configFeeID = configFeeID;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getStstsCount() {
        return ststsCount;
    }

    public void setStstsCount(String ststsCount) {
        this.ststsCount = ststsCount;
    }

}
