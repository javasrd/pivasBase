package com.zc.pivas.allowork.bean;

/**
 * 配置统计饼图
 *
 * @author jagger
 * @version 1.0
 */
public class AlloWorkPieBean {
    /**
     * 配置人员编码
     */
    private String pzCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 总数
     */
    private String value;

    /**
     * 配置费总额
     */
    private String cost;

    /**
     * 配置费分类
     */
    private String ruleName;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getPzCode() {
        return pzCode;
    }

    public void setPzCode(String pzCode) {
        this.pzCode = pzCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
