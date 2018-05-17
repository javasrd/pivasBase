package com.zc.pivas.allowork.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 药物使用统计
 *
 * @author jagger
 * @version 1.0
 */
public class AlloWork2BarBean implements Serializable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 配置费规则名称
     */
    private String ruleName;

    /**
     * 批次药单数量列表
     */
    private List<String> ydCountList = new ArrayList<String>();

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public List<String> getYdCountList() {
        return ydCountList;
    }

    public void setYdCountList(List<String> ydCountList) {
        this.ydCountList = ydCountList;
    }

    public void addYdCountList(String count) {
        this.ydCountList.add(count);
    }
}
