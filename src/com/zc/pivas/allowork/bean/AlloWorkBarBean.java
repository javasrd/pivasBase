package com.zc.pivas.allowork.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置工作量柱状图
 *
 * @author jagger
 * @version 1.0
 */
public class AlloWorkBarBean implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 配置分类名称
     */
    private List<String> ruleNameList = new ArrayList<String>();

    /**
     * 配置药师名称
     */
    private List<String> pzMcList = new ArrayList<String>();

    /**
     * 配置费分类数据
     */
    private List<AlloWork2BarBean> alloWork2BarList = new ArrayList<AlloWork2BarBean>();

    /**
     * 药品金额缓存
     */
    private Map<String, String> costMap = new HashMap<String, String>();

    public List<String> getRuleNameList() {
        return ruleNameList;
    }

    public void setRuleNameList(List<String> ruleNameList) {
        this.ruleNameList = ruleNameList;
    }

    public List<AlloWork2BarBean> getAlloWork2BarList() {
        return alloWork2BarList;
    }

    public void setAlloWork2BarList(List<AlloWork2BarBean> alloWork2BarList) {
        this.alloWork2BarList = alloWork2BarList;
    }

    public Map<String, String> getCostMap() {
        return costMap;
    }

    public void setCostMap(Map<String, String> costMap) {
        this.costMap = costMap;
    }

    public List<String> getPzMcList() {
        return pzMcList;
    }

    public void setPzMcList(List<String> pzMcList) {
        this.pzMcList = pzMcList;
    }
}
